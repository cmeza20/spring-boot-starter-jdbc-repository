package com.cmeza.spring.jdbc.repository.repositories.template;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.*;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericFactory;

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
    public JdbcQueryBuilder query(JdbcSelectFactory selectBuilder) {
        return getDialect()
                .query(selectBuilder)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return getDialect()
                .pagination(sql)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcPaginationBuilder pagination(JdbcSelectFactory selectBuilder) {
        return getDialect()
                .pagination(selectBuilder)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcUpdateBuilder update(String sql) {
        return getDialect()
                .update(sql)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcUpdateBuilder update(JdbcUpdateFactory jdbcUpdateFactory) {
        return getDialect()
                .update(jdbcUpdateFactory)
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

    @Override
    public JdbcExecuteBuilder execute(String sql) {
        return getDialect()
                .execute(sql)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcCallBuilder call(String callName) {
        return getDialect()
                .call(callName)
                .loggeable(jdbcRepositoryProperties.isLoggeable());
    }

    @Override
    public JdbcGenericFactory factories() {
        return getDialect().factories();
    }

    @Override
    public JdbcDatabaseMatadata getMetadata() {
        return getDialect().getMetadata();
    }

    @Override
    public String getRepositoryBeanName() {
        return getDialect().getRepositoryBeanName();
    }

}
