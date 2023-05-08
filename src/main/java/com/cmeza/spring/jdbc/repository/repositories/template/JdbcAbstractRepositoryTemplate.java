package com.cmeza.spring.jdbc.repository.repositories.template;

import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.Dialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultDialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultJdbcDatabaseMatadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.*;
import com.cmeza.spring.jdbc.repository.repositories.template.parsers.ParsedJdbcSql;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcNamedParameterUtils;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcProjectionSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentLruCache;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class JdbcAbstractRepositoryTemplate<T> extends NamedParameterJdbcTemplate implements ApplicationContextAware, BeanFactoryAware, BeanNameAware {
    private final ConcurrentLruCache<String, ParsedJdbcSql> parsedSqlCache = new ConcurrentLruCache<>(DEFAULT_CACHE_LIMIT, JdbcNamedParameterUtils::parseSqlStatement);
    private final List<JdbcProjectionSupport<?, ?>> jdbcProjectionSupports = new LinkedList<>();

    protected JdbcRepositoryProperties jdbcRepositoryProperties;
    private ApplicationContext applicationContext;
    private DefaultListableBeanFactory defaultListableBeanFactory;
    private JdbcRepositoryOperations jdbcRepositoryOperations;
    private String beanName;

    protected JdbcAbstractRepositoryTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource[] paramSources) {
        return this.getPreparedStatementCreator(sql, paramSources, null);
    }

    public PreparedStatementCreator getPreparedStatementCreator(String sql, SqlParameterSource[] paramSources, @Nullable Consumer<PreparedStatementCreatorFactory> customizer) {
        ParsedJdbcSql parsedSql = getParsedJdbcSql(sql);
        PreparedStatementCreatorFactory pscf = getPreparedStatementCreatorFactory(parsedSql, paramSources);
        if (customizer != null) {
            customizer.accept(pscf);
        }
        Object[] params = JdbcNamedParameterUtils.buildValueArray(parsedSql, paramSources, null);
        return pscf.newPreparedStatementCreator(params);
    }

    public PreparedStatementCreatorFactory getPreparedStatementCreatorFactory(ParsedJdbcSql parsedSql, SqlParameterSource[] paramSources) {
        String sqlToUse = JdbcNamedParameterUtils.substituteNamedParameters(parsedSql, paramSources);
        List<SqlParameter> declaredParameters = JdbcNamedParameterUtils.buildSqlParameterList(parsedSql, paramSources);
        return new PreparedStatementCreatorFactory(sqlToUse, declaredParameters);
    }

    public ParsedJdbcSql getParsedJdbcSql(String sql) {
        return this.parsedSqlCache.get(sql);
    }

    public <E> JdbcRowMapper<E> registerJdbcRowMapperBean(Class<E> clazz) {
        this.validateRowMapper(clazz);

        String[] beanNames = applicationContext.getBeanNamesForType(clazz);
        if (beanNames.length > 0) {
            return (JdbcRowMapper<E>) applicationContext.getBean(beanNames[0], clazz);
        } else {
            Constructor<?> resolvableConstructor = BeanUtils.getResolvableConstructor(clazz);
            Class<?> genericClass = JdbcUtils.getGenericClass(clazz);

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            for (Parameter parameter : resolvableConstructor.getParameters()) {
                ParameterizedType type = (ParameterizedType) parameter.getParameterizedType();
                if (type != null && type.getActualTypeArguments().length > 0 && type.getActualTypeArguments()[0].getTypeName().equals(genericClass.getName())) {
                    builder.addConstructorArgValue(genericClass);
                } else {
                    builder.addConstructorArgValue(applicationContext.getBean(parameter.getType()));
                }
            }

            AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
            beanDefinition.setPrimary(true);
            beanDefinition.setAutowireCandidate(true);
            beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

            defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName(), beanDefinition);
            return (JdbcRowMapper<E>) applicationContext.getBean(clazz);
        }
    }

    public void validateRowMapper(Class<?> rowMapperClass) {
        Class<?> genericClass = JdbcUtils.getGenericClass(rowMapperClass);
        Assert.isTrue(!(genericClass.isPrimitive() || genericClass.getName().startsWith("java.lang") || genericClass.getName().startsWith("java.util")), "RowMapper type is not supported: " + genericClass.getName());
    }

    private void initDialect() {
        JdbcDatabaseMatadata databaseMetaData = this.getDatabaseMetaData();

        Dialect dialect = Dialect.from(DatabaseDriver.fromProductName(databaseMetaData.getDatabaseProductName()));
        Map<Class<?>, JdbcProjectionSupport<?, ?>> jdbcProjectionSupportMap = jdbcProjectionSupports
                .stream().filter(p -> {
                    JdbcUtils.projectionClassValidate(p.getClass());
                    return true;
                }).collect(Collectors.toMap(JdbcProjectionSupport::getProjectionClass, Function.identity()));

        AbstractJdbcBuilder.Impl impl = new AbstractJdbcBuilder.Impl((JdbcRepositoryTemplate)(T)this, jdbcProjectionSupportMap, databaseMetaData, beanName);

        switch (dialect) {
            case MYSQL:
            case MARIADB:
            case SQLITE:
                jdbcRepositoryOperations = new MySQLDialect(impl);
                break;
            case ORACLE:
                jdbcRepositoryOperations = new OracleDialect(impl);
                break;
            case POSTGRESQL:
                jdbcRepositoryOperations = new PostgresSQLDialect(impl);
                break;
            case SQLSERVER:
                jdbcRepositoryOperations = new SqlServerDialect(impl);
                break;
            case INFORMIX:
                jdbcRepositoryOperations = new InformixDialect(impl);
                break;
            default:
                jdbcRepositoryOperations = new DefaultDialect(impl);
        }
    }

    private JdbcDatabaseMatadata getDatabaseMetaData() {
        try {
            return org.springframework.jdbc.support.JdbcUtils.extractDatabaseMetaData(
                    Objects.requireNonNull(this.getJdbcTemplate().getDataSource()),
                    dbmd -> new DefaultJdbcDatabaseMatadata(dbmd.getDatabaseProductName(), dbmd.getDatabaseProductVersion()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JdbcRepositoryOperations getDialect() {
        return jdbcRepositoryOperations;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.jdbcRepositoryProperties = applicationContext.getBean(JdbcRepositoryProperties.class);
        applicationContext.getBeansOfType(JdbcProjectionSupport.class).values()
                .forEach(jdbcProjectionSupports::add);

        this.initDialect();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
