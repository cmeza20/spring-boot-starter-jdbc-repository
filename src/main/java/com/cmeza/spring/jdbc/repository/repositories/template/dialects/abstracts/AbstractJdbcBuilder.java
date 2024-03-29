package com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts;

import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcMetadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcHeaderLog;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcLoggerUtils;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcProjectionSupport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public abstract class AbstractJdbcBuilder<T> implements JdbcBuilder<T>, JdbcMetadata<T> {

    public static final Logger log = LoggerFactory.getLogger(AbstractJdbcBuilder.class);
    private final MapSqlParameterSource mapParameterSource = new MapSqlParameterSource();
    private final List<SqlParameterSource> beanParameterSources = new ArrayList<>();
    private final Impl impl;
    protected boolean loggeable;
    protected String key;
    private RowMapper<?> rowMapper;
    private JdbcDatabaseMatadata databaseMetaData;

    protected AbstractJdbcBuilder(Impl impl) {
        this.impl = impl;
        this.databaseMetadata(impl.getDatabaseMetaData());
    }

    @Override
    public T withParameter(String parameterName, Object parameterValue) {
        if (Objects.nonNull(parameterValue) && parameterValue.getClass().isArray()) {
            mapParameterSource.addValue(parameterName, Arrays.asList((Object[]) parameterValue));
        } else {
            mapParameterSource.addValue(parameterName, parameterValue);
        }
        return (T) this;
    }

    @Override
    public T withParameter(String parameterName, Object parameterValue, int sqlType) {
        if (sqlType == 0) {
            return withParameter(parameterName, parameterValue);
        }

        if (Objects.nonNull(parameterValue) && parameterValue.getClass().isArray()) {
            mapParameterSource.addValue(parameterName, Arrays.asList((Object[]) parameterValue), sqlType);
        } else {
            mapParameterSource.addValue(parameterName, parameterValue, sqlType);
        }
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
                beanParameterSources.addAll(Arrays.asList(SqlParameterSourceUtils.createBatch(object)));
            }
        }
        return (T) this;
    }

    @Override
    public T withParameterList(List<?> objects) {
        if (Objects.nonNull(objects)) {
            beanParameterSources.addAll(Arrays.asList(SqlParameterSourceUtils.createBatch(objects)));
        }
        return (T) this;
    }

    @Override
    public T withParameterMap(Map<?, ?> objects) {
        if (Objects.nonNull(objects)) {
            beanParameterSources.addAll(Arrays.asList(SqlParameterSourceUtils.createBatch(objects)));
        }
        return (T) this;
    }

    @Override
    public T withParameterSet(Set<?> objects) {
        if (Objects.nonNull(objects)) {
            beanParameterSources.addAll(Arrays.asList(SqlParameterSourceUtils.createBatch(objects)));
        }
        return (T) this;
    }

    @Override
    public T withRowMapper(RowMapper<?> rowMapper) {
        if (Objects.nonNull(rowMapper)) {
            if (rowMapper instanceof JdbcRowMapper) {
                Class<T> mappedClass = ((JdbcRowMapper<T>) rowMapper).getMappedClass();
                ((JdbcRowMapper<T>) rowMapper).init((JdbcProjectionSupport<T, T>) impl.jdbcProjectionSupportMap.get(mappedClass));
            }
            this.rowMapper = rowMapper;
        }
        return (T) this;
    }

    @Override
    public T withRowMapper(Class<? extends RowMapper<?>> rowMapperClass) {
        if (Objects.nonNull(rowMapperClass)) {
            this.withRowMapper(impl.jdbcRepositoryTemplate.registerJdbcRowMapperBean(rowMapperClass));
        }
        return (T) this;
    }

    @Override
    public T loggeable(boolean loggeable) {
        if (loggeable) {
            this.loggeable = loggeable;
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
                    .setLoggeable(loggeable)
                    .setDatabaseMetaData(databaseMetaData)
                    .setClassName(((T) this).getClass().getSimpleName())
                    .setRowMapper(rowMapper)
                    .setJdbcRepositoryTemplateBeanName(impl.getJdbcRepositoryTemplateBeanName())
                    .setKey(this.key)
                    .setPrintExtras(this::printExtras);

            JdbcLoggerUtils.printHeaderLog(jdbcHeaderLog);
            result = supplier.get();
        } catch (Exception e) {
            throwable = e;
        } finally {
            JdbcLoggerUtils.printParametersLog(log, loggeable, getParameterSources());
            JdbcLoggerUtils.printResult(log, loggeable, result, mill);
        }

        if (Objects.nonNull(throwable)) {
            throw new RuntimeException(throwable);
        }

        return result;
    }

    @Override
    public SqlParameterSource[] getParameterSources() {
        return Stream.concat(beanParameterSources.stream(), mapParameterSource.getValues().isEmpty() ? Stream.empty() : Stream.of(mapParameterSource)).toArray(SqlParameterSource[]::new);
    }

    @Override
    public <E> RowMapper<E> getRowMapper() {
        return (RowMapper<E>) rowMapper;
    }

    @Override
    public MapSqlParameterSource getMapSqlParameterSource() {
        return mapParameterSource;
    }

    @Override
    public List<SqlParameterSource> getBeanParameterSources() {
        return beanParameterSources;
    }

    @Override
    public MapSqlParameterSource getMergedSqlParameterSource() {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParameterSource.getValues());
        beanParameterSources.forEach(bean ->
                Arrays.stream(Objects.requireNonNull(bean.getParameterNames()))
                        .forEach(name -> sqlParameterSource.addValue(name, bean.getValue(name), bean.getSqlType(name))));
        return sqlParameterSource;
    }

    @Override
    public T withKey(String key) {
        this.key = key;
        return (T) this;
    }

    protected <E> void createRowMapperIfnotExists(Class<E> returnType) {
        if (Objects.isNull(getRowMapper())) {
            boolean isSingleColumnMapperType = JdbcUtils.isSingleColumnMapperType(returnType);
            if (returnType.isAssignableFrom(Map.class)) {
                withRowMapper(new ColumnMapRowMapper());
            } else if (isSingleColumnMapperType) {
                withRowMapper(new SingleColumnRowMapper<>(returnType));
            } else {
                JdbcRowMapper<E> rm = JdbcRowMapper.newInstance(returnType);
                rm.init((JdbcProjectionSupport<E, E>) impl.jdbcProjectionSupportMap.get(rm.getMappedClass()));
                withRowMapper(rm);
            }
        }
    }

    protected void incompatibleRowMapperToMap() {
        if (Objects.nonNull(getRowMapper()) && !getRowMapper().getClass().isAssignableFrom(ColumnMapRowMapper.class)) {
            Assert.isNull(getRowMapper(), "The Map is not compatible with RowMapper");
        }
    }

    protected void rowMapperRequired() {
        Assert.notNull(getRowMapper(), "RowMapper required! or specific return type");
    }

    protected void resultTypeRequired(Class<?> resultType) {
        Assert.notNull(resultType, "Specific return type required.");
    }

    protected void parameterSourcesRequired() {
        Assert.isTrue(!(mapParameterSource.getValues().isEmpty() && beanParameterSources.isEmpty()), "ParameterSources required!");
    }

    @Getter
    @RequiredArgsConstructor
    public static class Impl {
        private final JdbcRepositoryTemplate jdbcRepositoryTemplate;
        private final Map<Class<?>, JdbcProjectionSupport<?, ?>> jdbcProjectionSupportMap;
        private final JdbcDatabaseMatadata databaseMetaData;
        private final String jdbcRepositoryTemplateBeanName;
    }

}
