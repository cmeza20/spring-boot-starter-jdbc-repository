package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractDialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.function.SqlServerFunctionBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination.SqlServerPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.update.SqlServerUpdateBuilder;

public class SqlServerDialect extends AbstractDialect implements JdbcRepositoryOperations {

    public SqlServerDialect(AbstractJdbcBuilder.Impl impl) {
        super(impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return new SqlServerPaginationBuilder(sql, impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(JdbcSelectFactory selectBuilder) {
        return new SqlServerPaginationBuilder(selectBuilder, impl);
    }

    @Override
    public JdbcUpdateBuilder update(String sql) {
        return new SqlServerUpdateBuilder(sql, impl);
    }

    @Override
    public JdbcUpdateBuilder update(JdbcUpdateFactory jdbcUpdateFactory) {
        return new SqlServerUpdateBuilder(jdbcUpdateFactory, impl);
    }

    @Override
    public JdbcRoutineBuilder function(String functionName) {
        return new SqlServerFunctionBuilder(functionName, impl);
    }
}
