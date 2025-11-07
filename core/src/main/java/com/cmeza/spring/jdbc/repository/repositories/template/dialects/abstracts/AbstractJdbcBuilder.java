package com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts;

import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;
import com.cmeza.spring.jdbc.repository.support.exceptions.JdbcException;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcMetadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.InParameterSourceProvider;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.MappingSourceProvider;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.ParameterSourceProvider;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.RowMapperSourceProvider;
import com.cmeza.spring.jdbc.repository.utils.JdbcHeaderLog;
import com.cmeza.spring.jdbc.repository.utils.JdbcLoggerUtils;
import com.cmeza.spring.jdbc.repository.utils.JdbcMapSqlParameterSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

import java.sql.Types;
import java.util.*;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public abstract class AbstractJdbcBuilder<T> implements JdbcGenericBuilder<T>, JdbcMetadata<T> {

    public static final Logger log = LoggerFactory.getLogger(AbstractJdbcBuilder.class);
    private final InParameterSourceProvider inParameterSourceProvider;
    private final ParameterSourceProvider parameterSourceProvider;
    private final MappingSourceProvider mappingSourceProvider;
    private final RowMapperSourceProvider<T> rowMapperSourceProvider;
    private final Impl impl;

    protected boolean loggable;
    protected String key;
    private JdbcDatabaseMatadata databaseMetaData;

    protected AbstractJdbcBuilder(Impl impl) {
        this.impl = impl;
        this.databaseMetadata(impl.getDatabaseMetaData());
        this.inParameterSourceProvider = new InParameterSourceProvider();
        this.mappingSourceProvider = new MappingSourceProvider();
        this.parameterSourceProvider = new ParameterSourceProvider(inParameterSourceProvider, mappingSourceProvider);
        this.rowMapperSourceProvider = new RowMapperSourceProvider<>(impl);
    }

    @Override
    public T withParameter(String parameterName, Object parameterValue) {
        parameterSourceProvider.addParameterSource(parameterName, parameterValue, Types.NULL);
        return (T) this;
    }

    @Override
    public T withParameter(String parameterName, Object parameterValue, int sqlType) {
        if (sqlType == Types.NULL) {
            return withParameter(parameterName, parameterValue);
        }

        parameterSourceProvider.addParameterSource(parameterName, parameterValue, sqlType);
        return (T) this;
    }

    @Override
    public T withParameter(Object object) {
        if (Objects.nonNull(object)) {
            if (object instanceof List) {
                return withParameterList((List<?>) object);
            } else if (object instanceof Map) {
                return withParameterMap((Map<?, ?>) object);
            } else if (object instanceof Set) {
                return withParameterSet((Set<?>) object);
            } else if (object.getClass().isArray()) {
                return withParameterList(Arrays.asList((Object[]) object));
            } else {
                parameterSourceProvider.addBeanParameterSource(object);
            }
        }
        return (T) this;
    }

    @Override
    public T withParameterList(List<?> objects) {
        parameterSourceProvider.addBeanParameterSourceList(objects);
        return (T) this;
    }

    @Override
    public T withParameterMap(Map<?, ?> objects) {
        parameterSourceProvider.addBeanParameterSourceMap(objects);
        return (T) this;
    }

    @Override
    public T withParameterSet(Set<?> objects) {
        parameterSourceProvider.addBeanParameterSourceSet(objects);
        return (T) this;
    }

    @Override
    public T withRowMapper(RowMapper<?> rowMapper) {
        this.rowMapperSourceProvider.setRowMapper(rowMapper);
        return (T) this;
    }

    @Override
    public T withRowMapper(Class<? extends RowMapper<?>> rowMapperClass) {
        this.rowMapperSourceProvider.setRowMapper(rowMapperClass);
        return (T) this;
    }

    @Override
    public T loggable(boolean loggable) {
        if (loggable) {
            this.loggable = loggable;
        }
        return (T) this;
    }

    @Override
    public T databaseMetadata(JdbcDatabaseMatadata databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
        return (T) this;
    }

    @Override
    public <R> R execute(Supplier<R> supplier) {
        long mill = System.currentTimeMillis();
        Throwable throwable = null;
        R result = null;
        try {
            JdbcHeaderLog jdbcHeaderLog = new JdbcHeaderLog()
                    .setLogger(log)
                    .setLoggable(loggable)
                    .setDatabaseMetaData(databaseMetaData)
                    .setClassName(this.getClass().getSimpleName())
                    .setRowMapper(rowMapperSourceProvider.getRowMapper())
                    .setJdbcRepositoryTemplateBeanName(impl.getJdbcRepositoryTemplateBeanName())
                    .setKey(this.key)
                    .setPrintExtras(this::printExtras);

            JdbcLoggerUtils.printHeaderLog(jdbcHeaderLog);
            result = supplier.get();
        } catch (Exception e) {
            throwable = e;
        } finally {
            JdbcLoggerUtils.printParametersLog(log, loggable, getParameterSources(), inParameterSourceProvider, mappingSourceProvider);
            JdbcLoggerUtils.printResult(log, loggable, result, mill);
        }

        if (Objects.nonNull(throwable)) {
            throw new JdbcException(throwable);
        }

        return result;
    }

    @Override
    public SqlParameterSource[] getParameterSources() {
        return parameterSourceProvider.getParameterSources();
    }

    @Override
    public <E> RowMapper<E> getRowMapper() {
        return (RowMapper<E>) rowMapperSourceProvider.getRowMapper();
    }

    @Override
    public JdbcMapSqlParameterSource getMapSqlParameterSource() {
        return parameterSourceProvider.getMapParameterSource();
    }

    @Override
    public List<SqlParameterSource> getBeanParameterSources() {
        return parameterSourceProvider.getBeanParameterSources();
    }

    @Override
    public MapSqlParameterSource getMergedSqlParameterSource() {
        return parameterSourceProvider.getMergedSqlParameterSource();
    }

    @Override
    public T withKey(String key) {
        this.key = key;
        return (T) this;
    }

    @Override
    public T withMapping(String to, int sqlType) {
        this.withMapping(to, null, sqlType);
        return (T) this;
    }

    @Override
    public T withMapping(String to, String from) {
        this.withMapping(to, from, Types.NULL);
        return (T) this;
    }

    @Override
    public T withMapping(String to, String from, int sqlType) {
        mappingSourceProvider.addMapping(to, from, sqlType);
        return (T) this;
    }

    @Override
    public T withMapping(MappingDefinition mappingDefinition) {
        mappingSourceProvider.addMapping(mappingDefinition);
        return (T) this;
    }

    protected void resultTypeRequired(Class<?> resultType) {
        Assert.notNull(resultType, "Specific return type required.");
    }

    //RowMappers
    protected <E> void createRowMapperIfnotExists(Class<E> returnType) {
        rowMapperSourceProvider.createRowMapperIfnotExists(returnType);
    }

    protected void incompatibleRowMapperToMap() {
        rowMapperSourceProvider.incompatibleRowMapperToMap();
    }

    protected void rowMapperRequired() {
        rowMapperSourceProvider.assertRowMapperRequired();
    }

    //ParameterSources
    protected void parameterSourcesRequired() {
        parameterSourceProvider.assertParameterSourcesRequired();
    }

    protected boolean isSetAndNotContainsInParameter(String parameterName) {
        return inParameterSourceProvider.isSetAndNotContains(parameterName);
    }

    protected boolean isInParameterNames() {
        return inParameterSourceProvider.isSetParameterNames();
    }

    protected T setInParameterNames(String... inParameterNames) {
        this.inParameterSourceProvider.setInParameterNames(inParameterNames);
        return (T) this;
    }

    protected String[] getInParameterNames() {
        return this.inParameterSourceProvider.getInParameterNames();
    }

    //Mappings
    protected Optional<MappingDefinition> findMappingByFrom(String from) {
        return mappingSourceProvider.findMappingByFrom(from);
    }

    protected List<MappingDefinition> getMappingDefinitions() {
        return mappingSourceProvider.getSortedMappingDefinitions();
    }

    protected boolean isSetMappings() {
        return mappingSourceProvider.isSetMappings();
    }

    @Getter
    @RequiredArgsConstructor
    public static class Impl {
        private final JdbcRepositoryTemplate jdbcRepositoryTemplate;
        private final JdbcDatabaseMatadata databaseMetaData;
        private final String jdbcRepositoryTemplateBeanName;
    }

}
