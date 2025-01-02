package com.cmeza.spring.jdbc.repository.repositories.executors;

import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.definitions.ParameterDefinition;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.Direction;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
public abstract class AbstractJdbcExecutor<T extends JdbcGenericBuilder<T>> implements JdbcExecutor {

    private JdbcConfiguration configuration;

    protected abstract Object execute(ReturnType returnType, T jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch);

    protected abstract T bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration);

    @Override
    public Object execute(MethodMetadata methodMetadata, Object[] arguments) {
        RowMapper<?> rowMapper = configuration.getRowMapper();
        TypeMetadata typeMetadata = configuration.getTypeMetadata();
        JdbcRepositoryTemplate jdbcTemplate = configuration.getJdbcTemplate();

        T builder = bindBuilder(jdbcTemplate, configuration);

        //Mappings
        Arrays.stream(configuration.getMappings()).forEach(builder::withMapping);

        List<Parameter> parameters = new ArrayList<>();

        int batchCount = this.processBuilder(builder, arguments, configuration.getParameters(), parameters);

        boolean isBatch = batchCount > 0;
        ReturnType returnType = ReturnType.from(typeMetadata);

        this.bindParameters(builder, parameters);

        return this.execute(returnType, builder, typeMetadata, configuration, rowMapper, isBatch);
    }

    @Override
    public void attachConfiguration(JdbcConfiguration jdbcConfiguration) {
        this.configuration = jdbcConfiguration;
        this.validateConfiguration();
        this.validateConfiguration(jdbcConfiguration);
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
    }

    @Override
    public void print() {
        if (log.isDebugEnabled()) {
            log.debug("Registered JdbcRepository: [{}] {}", getExecuteType(), configuration.getConfigKey());
        }
    }

    private void validateConfiguration() {
        Assert.notNull(configuration, "JdbcConfiguration required");
    }

    private int processBuilder(T builder, Object[] arguments, ParameterDefinition[] parameterDefinitions, List<Parameter> parameters) {
        Assert.notNull(builder, "JdbcBuilder required");

        int index = 0;
        int batchCount = 0;
        for (Object obj : arguments) {
            ParameterDefinition definition = parameterDefinitions[index];
            batchCount += bindParamaters(builder, definition, obj, parameters);
            index++;
        }

        return batchCount;
    }

    protected void bindParameters(T builder, List<Parameter> parameters) {
        parameters.forEach(param -> {
            if (param.isObject()) {
                builder.withParameter(param.getValue());
            } else {
                builder.withParameter(param.getName(), param.getValue(), param.getType());
            }
        });
    }

    protected void eachParameter(T builder, ParameterDefinition parameterDefinition, Object obj) {
    }

    private int bindParamaters(T builder, ParameterDefinition definition, Object obj, List<Parameter> parameters) {
        int batchCount = 0;
        if (Objects.nonNull(definition)) {
            if (Objects.isNull(obj)) {
                this.eachParameter(builder, definition, obj);
                parameters.add(new Parameter(definition, obj, false, Direction.IN));
                return 0;
            }

            Object inParam = obj.getClass().isArray() ? Arrays.asList((Object[]) obj) : obj;
            this.eachParameter(builder, definition, inParam);

            if (definition.isBean() || definition.isBatch()) {
                parameters.add(new Parameter(definition, inParam, true, Direction.IN));
                if (definition.isCollection()) {
                    batchCount++;
                }
            } else {
                parameters.add(new Parameter(definition, inParam, false, Direction.IN));
            }

        }
        return batchCount;
    }

    @Getter
    public static class Parameter {
        private final String name;
        private final int type;
        private final Object value;
        private final boolean isObject;
        private final Direction direction;

        public Parameter(String name, int type, Direction direction) {
            this.name = name;
            this.type = type;
            this.direction = direction;
            this.isObject = false;
            this.value = null;
        }

        public Parameter(ParameterDefinition parameterDefinition, Object value, boolean isObject, Direction direction) {
            this.name = parameterDefinition.getParameterName();
            this.type = parameterDefinition.getParameterType();
            this.value = value;
            this.isObject = isObject;
            this.direction = direction;
        }

    }
}
