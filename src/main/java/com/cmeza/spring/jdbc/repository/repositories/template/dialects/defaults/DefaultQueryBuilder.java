package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcQueryBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcNamedParameterUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.lang.NonNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class DefaultQueryBuilder extends AbstractJdbcBuilder<JdbcQueryBuilder> implements JdbcQueryBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultQueryBuilder.class);
    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;

    private JdbcSelectFactory selectBuilder;
    private String query;

    public DefaultQueryBuilder(JdbcSelectFactory selectBuilder, Impl impl) {
        super(impl);
        this.selectBuilder = selectBuilder;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    public DefaultQueryBuilder(String query, Impl impl) {
        super(impl);
        this.query = query;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    @Override
    public void printExtras(Logger logger) {
        if (Objects.nonNull(selectBuilder)) {
            selectBuilder.print(LOGGER);
            String sql = selectBuilder.generateQuery();
            logger.info("| Query: [{}]", sql);
        } else if (StringUtils.isNotEmpty(query)) {
            logger.info("| Query: [{}]", query);
        }
    }

    @Override
    public <R> R fetchOne() {
        return JdbcNamedParameterUtils.singleResult(fetchListInternal());
    }

    @Override
    public <R> R fetchOne(Class<R> resultType) {
        return JdbcNamedParameterUtils.singleResult(fetchListInternal(resultType));
    }

    @Override
    public <R> Optional<R> fetchOptional() {
        return Optional.ofNullable(JdbcNamedParameterUtils.singleResult(fetchListInternal()));
    }

    @Override
    public <R> Optional<R> fetchOptional(Class<R> resultType) {
        return Optional.ofNullable(JdbcNamedParameterUtils.singleResult(fetchListInternal(resultType)));
    }

    @Override
    public <R> List<R> fetchList() {
        return this.fetchListInternal();
    }

    @Override
    public <R> List<R> fetchList(Class<R> resultType) {
        return this.fetchListInternal(resultType);
    }

    @Override
    public <R> Set<R> fetchSet() {
        return new HashSet<>(this.fetchListInternal());
    }

    @Override
    public <R> Set<R> fetchSet(Class<R> resultType) {
        return new HashSet<>(this.fetchListInternal(resultType));
    }

    @Override
    public <R> Stream<R> fetchStream() {
        return this.fetchStreamInternal();
    }

    @Override
    public <R> Stream<R> fetchStream(Class<R> resultType) {
        return this.fetchStreamInternal(resultType);
    }

    @Override
    public <R> R[] fetchArray(Class<R> resultType) {
        this.createRowMapperIfnotExists(resultType);
        List<R> list = this.fetchListInternal(resultType);
        R[] arr = (R[]) Array.newInstance(resultType, list.size());
        return list.toArray(arr);
    }

    @Override
    public Map<String, Object> fetchMap() {
        this.incompatibleRowMapperToMap();
        return execute(() -> JdbcNamedParameterUtils.singleResult(jdbcRepositoryTemplate.getJdbcOperations().query(getPreparedStatementCreator(), new ColumnMapRowMapper())));
    }

    private PreparedStatementCreator getPreparedStatementCreator() {
        return jdbcRepositoryTemplate.getPreparedStatementCreator(getQuery(), getParameterSources());
    }

    private <T> List<T> fetchListInternal() {
        return execute(() -> {
            this.rowMapperRequired();
            return jdbcRepositoryTemplate.getJdbcOperations().query(getPreparedStatementCreator(), getRowMapper());
        });
    }

    private <T> List<T> fetchListInternal(@NonNull Class<T> resultType) {
        return execute(() -> {
            this.resultTypeRequired(resultType);
            this.createRowMapperIfnotExists(resultType);
            return jdbcRepositoryTemplate.getJdbcOperations().query(getPreparedStatementCreator(), getRowMapper());
        });
    }

    private <T> Stream<T> fetchStreamInternal() {
        return execute(() -> {
            this.rowMapperRequired();
            return jdbcRepositoryTemplate.getJdbcOperations().queryForStream(getPreparedStatementCreator(), getRowMapper());
        });
    }

    private <T> Stream<T> fetchStreamInternal(@NonNull Class<T> resultType) {
        return execute(() -> {
            this.resultTypeRequired(resultType);
            this.createRowMapperIfnotExists(resultType);
            return jdbcRepositoryTemplate.getJdbcOperations().queryForStream(getPreparedStatementCreator(), getRowMapper());
        });
    }

    private String getQuery() {
        if (Objects.nonNull(selectBuilder)) {
            return selectBuilder.generateQuery();
        }
        return query;
    }
}
