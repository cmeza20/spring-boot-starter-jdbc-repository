package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractDialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.function.MysqlFunctionBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination.MySQLPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.update.MySQLUpdateBuilder;

public class MySQLDialect extends AbstractDialect implements JdbcRepositoryOperations {

    public MySQLDialect(AbstractJdbcBuilder.Impl impl) {
        super(impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return new MySQLPaginationBuilder(sql, impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(JdbcSelectFactory selectBuilder) {
        return new MySQLPaginationBuilder(selectBuilder, impl);
    }

    @Override
    public JdbcUpdateBuilder update(String sql) {
        return new MySQLUpdateBuilder(sql, impl);
    }

    @Override
    public JdbcUpdateBuilder update(JdbcUpdateFactory jdbcUpdateFactory) {
        return new MySQLUpdateBuilder(jdbcUpdateFactory, impl);
    }

    @Override
    public JdbcRoutineBuilder function(String functionName) {
        return new MysqlFunctionBuilder(functionName, impl);
    }
}
