package com.cmeza.spring.jdbc.repository.repositories.template.dialects;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.*;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericFactory;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;

public interface JdbcRepositoryOperations {
    JdbcQueryBuilder query(String query);

    JdbcQueryBuilder query(JdbcSelectFactory selectFactory);

    JdbcPaginationBuilder pagination(String sql);

    JdbcPaginationBuilder pagination(JdbcSelectFactory selectBuilder);

    JdbcUpdateBuilder update(String sql);

    JdbcUpdateBuilder update(JdbcUpdateFactory jdbcUpdateFactory);

    JdbcInsertBuilder insert(String tableName);

    JdbcRoutineBuilder function(String functionName);

    JdbcRoutineBuilder procedure(String procedureName);

    JdbcExecuteBuilder execute(String sql);

    JdbcCallBuilder call(String callName);

    JdbcGenericFactory factories();

    JdbcDatabaseMatadata getMetadata();

    String getRepositoryBeanName();
}
