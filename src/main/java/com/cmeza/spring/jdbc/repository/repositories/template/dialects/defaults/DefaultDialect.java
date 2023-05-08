package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.*;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractDialect;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.*;

public class DefaultDialect extends AbstractDialect implements JdbcRepositoryOperations {

    public DefaultDialect(AbstractJdbcBuilder.Impl impl) {
        super(impl);
    }

    @Override
    public JdbcQueryBuilder query(String sql) {
        return new DefaultQueryBuilder(sql, impl);
    }

    @Override
    public JdbcPaginationBuilder pagination(String sql) {
        return new DefaultPaginationBuilder(sql, impl);
    }

    @Override
    public JdbcUpdateBuilder update(String sql) {
        return new DefaultUpdateBuilder(sql, impl);
    }

    @Override
    public JdbcBatchUpdateBuilder batchUpdate(String sql) {
        return new DefaultBatchUpdateBuilder(sql, impl);
    }

    @Override
    public JdbcInsertBuilder insert(String tableName) {
        return new DefaultInsertBuilder(tableName, impl);
    }

    @Override
    public JdbcRoutineBuilder function(String functionName) {
        return new DefaultFunctionBuilder(functionName, impl);
    }

    @Override
    public JdbcRoutineBuilder procedure(String procedureName) {
        return new DefaultProcedureBuilder(procedureName, impl);
    }
}
