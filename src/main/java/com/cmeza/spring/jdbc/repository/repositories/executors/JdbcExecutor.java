package com.cmeza.spring.jdbc.repository.repositories.executors;

import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;

public interface JdbcExecutor {
    ExecutorType getExecuteType();

    Object execute(MethodMetadata methodMetadata, Object[] arguments);

    void attachConfiguration(JdbcConfiguration jdbcConfiguration);

    void validateConfiguration(JdbcConfiguration jdbcConfiguration);

    void print();
}
