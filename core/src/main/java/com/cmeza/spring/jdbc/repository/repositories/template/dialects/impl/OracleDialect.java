package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractDialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.function.OracleFunctionBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination.OraclePaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.procedure.OracleProcedureBuilder;

public class OracleDialect extends AbstractDialect implements JdbcRepositoryOperations {

    public OracleDialect(AbstractJdbcBuilder.Impl impl) {
        super(impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return new OraclePaginationBuilder(sql, impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(JdbcSelectFactory selectBuilder) {
        return new OraclePaginationBuilder(selectBuilder, impl);
    }

    @Override
    public JdbcRoutineBuilder function(String functionName) {
        return new OracleFunctionBuilder(functionName, impl);
    }

    @Override
    public JdbcRoutineBuilder procedure(String procedureName) {
        return new OracleProcedureBuilder(procedureName, impl);
    }
}
