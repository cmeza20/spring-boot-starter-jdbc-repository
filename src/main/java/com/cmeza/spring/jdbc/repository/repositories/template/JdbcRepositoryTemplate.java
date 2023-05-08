package com.cmeza.spring.jdbc.repository.repositories.template;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.*;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.*;

import javax.sql.DataSource;

public class JdbcRepositoryTemplate extends JdbcAbstractRepositoryTemplate<JdbcRepositoryTemplate> implements JdbcRepositoryOperations {

    public JdbcRepositoryTemplate(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public JdbcQueryBuilder query(String sql) {
        return getDialect()
                .query(sql)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return getDialect()
                .pagination(sql)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcUpdateBuilder update(String sql) {
        return getDialect()
                .update(sql)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcBatchUpdateBuilder batchUpdate(String sql) {
        return getDialect()
                .batchUpdate(sql)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcInsertBuilder insert(String tableName) {
        return getDialect()
                .insert(tableName)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcRoutineBuilder function(String functionName) {
        return getDialect()
                .function(functionName)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcRoutineBuilder procedure(String procedureName) {
        return getDialect()
                .procedure(procedureName)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

}
