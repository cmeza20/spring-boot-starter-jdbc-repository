package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.UpdateReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import java.util.Objects;

public class JdbcUpdateExecutor extends AbstractJdbcExecutor<JdbcUpdateBuilder> {

    private static final String SUPPORT_MESSAGE = " - @JdbcUpdate only supports the following response types: Void, Integer, KeyHolder";
    private final JdbcUpdate jdbcUpdate;
    private String[] keyColumnNames;

    public JdbcUpdateExecutor(JdbcUpdate jdbcUpdate) {
        String query = jdbcUpdate.value();
        Assert.notNull(query, "Sql required");
        Assert.hasLength(query, "Sql is empty");
        if (Objects.nonNull(jdbcUpdate.keyColumnNames()) && jdbcUpdate.keyColumnNames().length > 0) {
            this.keyColumnNames = jdbcUpdate.keyColumnNames();
        }
        this.jdbcUpdate = jdbcUpdate;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.UPDATE;
    }

    @Override
    protected Object execute(ReturnType returnType, JdbcUpdateBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        if (isBatch) {
            throw new UnsupportedOperationException(configuration.getConfigKey() + " - @JdbcUpdate does not support batch parameters, use @JdbcBatchUpdate annotation instead");
        }
        UpdateReturnType updateReturnType = UpdateReturnType.from(returnType);
        return updateReturnType.execute(jdbcBuilder);
    }

    @Override
    protected JdbcUpdateBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {
        return jdbcTemplate.update(jdbcUpdate.value())
                .withKeyColumnNames(keyColumnNames)
                .withKey(configuration.getConfigKey())
                .loggeable(configuration.isLoggeable());
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        Assert.isTrue(
                typeMetadata.isVoid() ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), int.class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), Integer.class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), KeyHolder.class), jdbcConfiguration.getConfigKey() + SUPPORT_MESSAGE);
    }
}
