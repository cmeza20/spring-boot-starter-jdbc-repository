package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcCallExecutor;

import java.util.Map;

public class CallAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcCall> {
    private static final String PARAMETERS = "parameters";

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcCall annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(VALUE, propertiesResolver.resolveRequiredPlaceholders(annotation.value()));
        if (annotation.schema().isEmpty()) {
            annotationValues.put(SCHEMA, jdbcRepository.schema());
        } else {
            annotationValues.put(SCHEMA, propertiesResolver.resolveRequiredPlaceholders(annotation.schema()));
        }
        if (annotation.catalog().isEmpty()) {
            annotationValues.put(CATALOG, jdbcRepository.catalog());
        } else {
            annotationValues.put(CATALOG, propertiesResolver.resolveRequiredPlaceholders(annotation.catalog()));
        }

        NamingStrategy namingStrategy = this.extractNamingStrategy(annotation.parametersNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            annotationValues.put(PARAMETERS, this.executeNamingStrategy(annotation.parameters(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcCall annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcCallExecutor(annotation);
    }
}
