package com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcRepositoryOperations;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.*;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.*;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.generics.DefaultFactories;

public abstract class AbstractDialect implements JdbcRepositoryOperations {
    protected final AbstractJdbcBuilder.Impl impl;

    protected AbstractDialect(AbstractJdbcBuilder.Impl impl) {
        this.impl = impl;
    }

    @Override
    public JdbcQueryBuilder query(String query) {
        return new DefaultQueryBuilder(query, impl);
    }

    @Override
    public JdbcQueryBuilder query(JdbcSelectFactory selectBuilder) {
        return new DefaultQueryBuilder(selectBuilder, impl);
    }

    @Override
    public JdbcUpdateBuilder update(String sql) {
        return new DefaultUpdateBuilder(sql, impl);
    }

    @Override
    public JdbcUpdateBuilder update(JdbcUpdateFactory jdbcUpdateFactory) {
        return new DefaultUpdateBuilder(jdbcUpdateFactory, impl);
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

    @Override
    public JdbcExecuteBuilder execute(String sql) {
        return new DefaultExecuteBuilder(sql, impl);
    }

    @Override
    public JdbcCallBuilder call(String callName) {
        return new DefaultCallBuilder(callName, impl);
    }

    @Override
    public JdbcGenericFactory factories() {
        return new DefaultFactories();
    }

    @Override
    public JdbcDatabaseMatadata getMetadata() {
        return impl.getDatabaseMetaData();
    }

    @Override
    public String getRepositoryBeanName() {
        return impl.getJdbcRepositoryTemplateBeanName();
    }
}
