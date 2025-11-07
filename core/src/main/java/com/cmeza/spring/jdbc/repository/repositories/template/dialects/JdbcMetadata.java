package com.cmeza.spring.jdbc.repository.repositories.template.dialects;

import com.cmeza.spring.jdbc.repository.utils.JdbcMapSqlParameterSource;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;
import java.util.function.Supplier;

public interface JdbcMetadata<T> {
    T databaseMetadata(JdbcDatabaseMatadata databaseMetaData);

    <R> R execute(Supplier<R> supplier);

    void printExtras(Logger logger);

    SqlParameterSource[] getParameterSources();

    <E> RowMapper<E> getRowMapper();

    JdbcMapSqlParameterSource getMapSqlParameterSource();

    List<SqlParameterSource> getBeanParameterSources();

    MapSqlParameterSource getMergedSqlParameterSource();

}
