package com.cmeza.spring.jdbc.repository.repositories.template.dialects;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.*;

public interface JdbcRepositoryOperations {
    JdbcQueryBuilder query(String sql);

    JdbcPaginationBuilder pagination(String sql);

    JdbcUpdateBuilder update(String sql);

    JdbcBatchUpdateBuilder batchUpdate(String sql);

    JdbcInsertBuilder insert(String tableName);

    JdbcRoutineBuilder function(String functionName);

    JdbcRoutineBuilder procedure(String procedureName);
}
