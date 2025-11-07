package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.UpdateReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcUpdateFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class JdbcUpdateExecutor extends AbstractJdbcExecutor<JdbcUpdateBuilder> {

    private static final String SUPPORT_MESSAGE = " - @JdbcUpdate only supports the following response types: Void, Integer, KeyHolder";
    private JdbcUpdate jdbcUpdate;
    private JdbcRawUpdate jdbcRawUpdate;
    private String[] keyColumnNames;

    public JdbcUpdateExecutor(JdbcRawUpdate jdbcRawUpdate) {
        String query = jdbcRawUpdate.value();
        Assert.hasLength(query, "Sql required");
        if (Objects.nonNull(jdbcRawUpdate.keyColumnNames()) && jdbcRawUpdate.keyColumnNames().length > 0) {
            this.keyColumnNames = jdbcRawUpdate.keyColumnNames();
        }
        this.jdbcRawUpdate = jdbcRawUpdate;
    }

    public JdbcUpdateExecutor(JdbcUpdate jdbcUpdate) {
        Assert.hasLength(jdbcUpdate.table(), "Table name required");
        Assert.notEmpty(jdbcUpdate.updateSets(), "UpdateSets required");
        Assert.state(!StringUtils.hasLength(jdbcUpdate.where()) || !jdbcUpdate.where().toUpperCase().contains("WHERE"), "Query 'WHERE' command not required");

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
        if (Objects.nonNull(jdbcUpdate)) {
            JdbcUpdateFactory updateFactory = jdbcTemplate.factories().update(jdbcUpdate.table(), jdbcUpdate.updateSets())
                    .withCatalog(jdbcUpdate.catalog())
                    .withSchema(jdbcUpdate.schema())
                    .withAlias(jdbcUpdate.alias())
                    .withWhere(jdbcUpdate.where())
                    .withJoinTable(configuration.getJoinTables())
                    .withFromTable(configuration.getFromTable());

            return jdbcTemplate.update(updateFactory)
                    .withKeyColumnNames(keyColumnNames)
                    .withKey(configuration.getConfigKey())
                    .loggable(configuration.isLoggable());
        }
        return jdbcTemplate.update(jdbcRawUpdate.value())
                .withKeyColumnNames(keyColumnNames)
                .withKey(configuration.getConfigKey())
                .loggable(configuration.isLoggable());
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
