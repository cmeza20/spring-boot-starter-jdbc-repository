package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractDialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.function.InformixFunctionBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination.InformixPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.procedure.InformixProcedureBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.update.InformixUpdateBuilder;

public class InformixDialect extends AbstractDialect implements JdbcRepositoryOperations {

    public InformixDialect(AbstractJdbcBuilder.Impl impl) {
        super(impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return new InformixPaginationBuilder(sql, impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(JdbcSelectFactory selectBuilder) {
        return new InformixPaginationBuilder(selectBuilder, impl);
    }

    @Override
    public JdbcUpdateBuilder update(String sql) {
        return new InformixUpdateBuilder(sql, impl);
    }

    @Override
    public JdbcUpdateBuilder update(JdbcUpdateFactory jdbcUpdateFactory) {
        return new InformixUpdateBuilder(jdbcUpdateFactory, impl);
    }

    @Override
    public JdbcRoutineBuilder procedure(String procedureName) {
        return new InformixProcedureBuilder(procedureName, impl);
    }

    @Override
    public JdbcRoutineBuilder function(String functionName) {
        return new InformixFunctionBuilder(functionName, impl);
    }
}
