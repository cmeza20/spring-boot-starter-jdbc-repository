package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcBatchUpdate;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcBatchUpdateBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class JdbcBatchUpdateExecutor extends AbstractJdbcExecutor<JdbcBatchUpdateBuilder> {

    private static final String SUPPORT_MESSAGE = " - @JdbcBatchUpdate only supports the following response types: Void, Integer[]";
    private final JdbcBatchUpdate jdbcBatchUpdate;

    public JdbcBatchUpdateExecutor(JdbcBatchUpdate jdbcBatchUpdate) {
        Assert.notNull(jdbcBatchUpdate.value(), "Sql required");
        Assert.hasLength(jdbcBatchUpdate.value(), "Sql is empty");
        this.jdbcBatchUpdate = jdbcBatchUpdate;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.BATCH_UPDATE;
    }

    @Override
    protected Object execute(ReturnType returnType, JdbcBatchUpdateBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        return jdbcBuilder.execute();
    }

    @Override
    protected JdbcBatchUpdateBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {
        return jdbcTemplate.batchUpdate(jdbcBatchUpdate.value())
                .loggeable(configuration.isLoggeable());
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        Assert.isTrue(
                typeMetadata.isVoid() ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), int[].class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), Integer[].class), jdbcConfiguration.getConfigKey() + SUPPORT_MESSAGE);
    }

}
