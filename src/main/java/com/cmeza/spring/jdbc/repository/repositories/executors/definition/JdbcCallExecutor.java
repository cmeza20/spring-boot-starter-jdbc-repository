package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.CallReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcCallBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

public class JdbcCallExecutor extends AbstractJdbcExecutor<JdbcCallBuilder> {

    private static final String SUPPORT_MESSAGE = " - @JdbcCall only supports Void type";
    private final JdbcCall jdbcCall;

    public JdbcCallExecutor(JdbcCall jdbcCall) {
        Assert.hasLength(jdbcCall.value(), "Call name required");
        this.jdbcCall = jdbcCall;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.EXECUTE;
    }

    @Override
    protected Object execute(ReturnType returnType, JdbcCallBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        CallReturnType callReturnType = CallReturnType.from(returnType);
        return callReturnType.execute(jdbcBuilder);
    }

    @Override
    protected JdbcCallBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {
        return jdbcTemplate.call(jdbcCall.value())
                .withSchema(jdbcCall.schema())
                .withCatalog(jdbcCall.catalog())
                .withParameters(jdbcCall.parameters())
                .withType(jdbcCall.type())
                .withKey(configuration.getConfigKey())
                .loggeable(configuration.isLoggeable());
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        Assert.isTrue(typeMetadata.isVoid(), jdbcConfiguration.getConfigKey() + SUPPORT_MESSAGE);
    }
}
