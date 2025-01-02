package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.Direction;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.RoutineReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

public class JdbcProcedureExecutor extends AbstractJdbcExecutor<JdbcRoutineBuilder> {

    private final JdbcProcedure jdbcProcedure;

    public JdbcProcedureExecutor(JdbcProcedure jdbcProcedure) {
        Assert.notNull(jdbcProcedure.name(), "Procedure name required");
        Assert.hasLength(jdbcProcedure.name(), "Procedure name is empty");
        this.jdbcProcedure = jdbcProcedure;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.PROCEDURE;
    }

    @Override
    protected Object execute(ReturnType returnType, JdbcRoutineBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        if (isBatch) {
            throw new UnsupportedOperationException(configuration.getConfigKey() + " - @JdbcProcedure does not support batch parameters, use @JdbcBatchUpdate annotation instead");
        }
        RoutineReturnType routineReturnType = RoutineReturnType.from(returnType);
        if (routineReturnType.equals(RoutineReturnType.MAP) && !(rowMapper instanceof ColumnMapRowMapper)) {
            Assert.isNull(rowMapper, configuration.getConfigKey() + " - Map is not compatible with RowMapper");
        }

        return routineReturnType.execute(jdbcBuilder, rowMapper, typeMetadata.getArgumentClass());
    }

    @Override
    protected JdbcRoutineBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {
        return jdbcTemplate.procedure(jdbcProcedure.name())
                .withCatalog(jdbcProcedure.catalog())
                .withSchema(jdbcProcedure.schema())
                .withInParameterNames(jdbcProcedure.inParameterNames())
                .withAccessCallParameterMetaData(jdbcProcedure.accessCallParameterMetaData())
                .withRowMapperParameterName(jdbcProcedure.rowMapperParameterName())
                .withKey(configuration.getConfigKey())
                .loggeable(configuration.isLoggeable());
    }

    @Override
    protected void bindParameters(JdbcRoutineBuilder builder, List<Parameter> parameters) {
        Arrays.stream(jdbcProcedure.outParameters()).forEach(param -> {
            Parameter parameter = new Parameter(param.value(), param.type(), Direction.OUT);
            if (param.order() != 0 && param.order() <= parameters.size()) {
                parameters.add(param.order() - 1, parameter);
            } else {
                parameters.add(parameter);
            }
        });

        parameters.forEach(param -> {
            if (param.getDirection().equals(Direction.OUT)) {
                builder.withOutParameter(param.getName(), param.getType());
                return;
            }
            if (param.isObject()) {
                builder.withParameter(param.getValue());
            } else {
                builder.withParameter(param.getName(), param.getValue(), param.getType());
            }
        });

    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        String configKey = jdbcConfiguration.getConfigKey();
        Assert.isTrue(!typeMetadata.isVoid(), configKey + " - Void return type is not supported, use @JdbcExecute annotation instead");
        Assert.isTrue(!typeMetadata.isEnum(), configKey + " - Enum return type is not supported");
        Assert.isTrue(!typeMetadata.isMapEntry(), configKey + " - Map.Entry return type is not supported");
        Assert.isTrue(!typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), JdbcPage.class), configKey + " - JdbcPage return type is not supported, use @JdbcPagination annotation instead");
    }


}
