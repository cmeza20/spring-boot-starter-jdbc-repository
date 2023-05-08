package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcQueryBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcNamedParameterUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.lang.NonNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class DefaultQueryBuilder extends AbstractJdbcBuilder<JdbcQueryBuilder> implements JdbcQueryBuilder {

    private final String query;
    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;

    public DefaultQueryBuilder(String query, Impl impl) {
        super(impl);
        this.query = query;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    @Override
    public void printExtras(Logger logger) {
        logger.info("| Query: [{}]", query);
    }

    @Override
    public <R> R fetchOne() {
        return execute(() -> JdbcNamedParameterUtils.singleResult(fetchListInternal()));
    }

    @Override
    public <R> R fetchOne(Class<R> resultType) {
        return execute(() -> JdbcNamedParameterUtils.singleResult(fetchListInternal(resultType)));
    }

    @Override
    public <R> Optional<R> fetchOptional() {
        return execute(() -> Optional.ofNullable(JdbcNamedParameterUtils.singleResult(fetchListInternal())));
    }

    @Override
    public <R> Optional<R> fetchOptional(Class<R> resultType) {
        return execute(() -> Optional.ofNullable(JdbcNamedParameterUtils.singleResult(fetchListInternal(resultType))));
    }

    @Override
    public <R> List<R> fetchList() {
        return execute(this::fetchListInternal);
    }

    @Override
    public <R> List<R> fetchList(Class<R> resultType) {
        return execute(() -> this.fetchListInternal(resultType));
    }

    @Override
    public <R> Set<R> fetchSet() {
        return execute(() -> new HashSet<>(this.fetchListInternal()));
    }

    @Override
    public <R> Set<R> fetchSet(Class<R> resultType) {
        return execute(() -> new HashSet<>(this.fetchListInternal(resultType)));
    }

    @Override
    public <R> Stream<R> fetchStream() {
        return execute(this::fetchStreamInternal);
    }

    @Override
    public <R> Stream<R> fetchStream(Class<R> resultType) {
        return execute(() -> this.fetchStreamInternal(resultType));
    }

    @Override
    public <R> R[] fetchArray(Class<R> resultType) {
        return execute(() -> {
            this.createRowMapperIfnotExists(resultType);
            List<R> list = this.fetchListInternal(resultType);
            R[] arr = (R[]) Array.newInstance(resultType, list.size());
            return list.toArray(arr);
        });
    }

    @Override
    public Map<String, Object> fetchMap() {
        this.incompatibleRowMapperToMap();
        return execute(() -> JdbcNamedParameterUtils.singleResult(jdbcRepositoryTemplate.getJdbcOperations().query(getPreparedStatementCreator(), new ColumnMapRowMapper())));
    }

    private PreparedStatementCreator getPreparedStatementCreator() {
        return jdbcRepositoryTemplate.getPreparedStatementCreator(query, getParameterSources());
    }

    private <T> List<T> fetchListInternal() {
        this.rowMapperRequired();
        return jdbcRepositoryTemplate.getJdbcOperations().query(getPreparedStatementCreator(), getRowMapper());
    }

    private <T> List<T> fetchListInternal(@NonNull Class<T> resultType) {
        this.resultTypeRequired(resultType);
        this.createRowMapperIfnotExists(resultType);
        return jdbcRepositoryTemplate.getJdbcOperations().query(getPreparedStatementCreator(), getRowMapper());
    }

    private <T> Stream<T> fetchStreamInternal() {
        this.rowMapperRequired();
        return jdbcRepositoryTemplate.getJdbcOperations().queryForStream(getPreparedStatementCreator(), getRowMapper());
    }

    private <T> Stream<T> fetchStreamInternal(@NonNull Class<T> resultType) {
        this.resultTypeRequired(resultType);
        this.createRowMapperIfnotExists(resultType);
        return jdbcRepositoryTemplate.getJdbcOperations().queryForStream(getPreparedStatementCreator(), getRowMapper());
    }

}
