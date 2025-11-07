package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.ExecuteReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcExecuteBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class JdbcExecuteExecutor extends AbstractJdbcExecutor<JdbcExecuteBuilder> {

    private static final String SUPPORT_MESSAGE = " - @JdbcBatchUpdate only supports the following response types: Void, Integer, Integer[]";
    private final JdbcExecute jdbcExecute;

    public JdbcExecuteExecutor(JdbcExecute jdbcExecute) {
        Assert.hasLength(jdbcExecute.value(), "Sql required");
        this.jdbcExecute = jdbcExecute;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.EXECUTE;
    }

    @Override
    protected Object execute(ReturnType returnType, JdbcExecuteBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        ExecuteReturnType executeReturnType = ExecuteReturnType.from(returnType);
        return executeReturnType.execute(jdbcBuilder);
    }

    @Override
    protected JdbcExecuteBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {
        return jdbcTemplate.execute(jdbcExecute.value())
                .withKey(configuration.getConfigKey())
                .loggable(configuration.isLoggable());
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        Assert.isTrue(
                typeMetadata.isVoid() ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), int.class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), int[].class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), Integer[].class), jdbcConfiguration.getConfigKey() + SUPPORT_MESSAGE);
    }
}
