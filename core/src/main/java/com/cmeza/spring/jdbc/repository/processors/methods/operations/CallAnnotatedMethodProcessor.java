package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcCallProperties;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcCallParser;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcCallExecutor;

import java.util.Map;
import java.util.Objects;

public class CallAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcCall, JdbcCallProperties> {

    public CallAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcCall annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcCallExecutor(annotation);
    }

    @Override
    protected JdbcCallProperties dslLocator(JdbcCall annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcCallProperties jdbcCallProperties = dslProperties.getCall();
        if (Objects.isNull(jdbcCallProperties)) {
            jdbcCallProperties = new JdbcCallProperties();
        }

        return jdbcCallProperties;
    }

    @Override
    protected void dslParser(JdbcCall annotation, JdbcCallProperties dsl) {
        PARSER.getParser(JdbcCallParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcCallProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setValue(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getValue(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCall.class, JdbcConstants.VALUE)));

        if (dslProperty.getSchema().isEmpty()) {
            dslProperty.setSchema(jdbcRepository.schema());
        } else {
            dslProperty.setSchema(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getSchema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCall.class, JdbcConstants.SCHEMA)));
        }
        if (dslProperty.getCatalog().isEmpty()) {
            dslProperty.setCatalog(jdbcRepository.catalog());
        } else {
            dslProperty.setCatalog(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getCatalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCall.class, JdbcConstants.CATALOG)));
        }

        NamingStrategy namingStrategy = this.extractNamingStrategy(dslProperty.getParametersNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            dslProperty.setParameters(this.executeNamingStrategy(dslProperty.getParameters(), namingStrategy));
        }
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcCallProperties dslProperty) {
        //updateValues
    }


}
