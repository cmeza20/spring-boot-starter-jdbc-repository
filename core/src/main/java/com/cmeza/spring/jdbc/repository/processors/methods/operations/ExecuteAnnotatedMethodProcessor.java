package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcExecuteExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcExecuteParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcExecuteProperties;

import java.util.Map;
import java.util.Objects;

public class ExecuteAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcExecute, JdbcExecuteProperties> {
    public ExecuteAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcExecute annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcExecuteExecutor(annotation);
    }

    @Override
    protected JdbcExecuteProperties dslLocator(JdbcExecute annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcExecuteProperties jdbcExecuteProperties = dslProperties.getExecute();
        if (Objects.isNull(jdbcExecuteProperties)) {
            jdbcExecuteProperties = new JdbcExecuteProperties();
        }

        return jdbcExecuteProperties;
    }

    @Override
    protected void dslParser(JdbcExecute annotation, JdbcExecuteProperties dsl) {
        PARSER.getParser(JdbcExecuteParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcExecuteProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setValue(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getValue(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcExecute.class, JdbcConstants.VALUE)));
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcExecuteProperties dslProperty) {
        //updateValues
    }
}
