package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractDialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination.PostgresPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.procedure.PostgresProcedureBuilder;

public class PostgresSQLDialect extends AbstractDialect implements JdbcRepositoryOperations {
    public PostgresSQLDialect(AbstractJdbcBuilder.Impl impl) {
        super(impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return new PostgresPaginationBuilder(sql, impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(JdbcSelectFactory selectBuilder) {
        return new PostgresPaginationBuilder(selectBuilder, impl);
    }

    @Override
    public JdbcRoutineBuilder procedure(String procedureName) {
        return new PostgresProcedureBuilder(procedureName, impl);
    }
}
