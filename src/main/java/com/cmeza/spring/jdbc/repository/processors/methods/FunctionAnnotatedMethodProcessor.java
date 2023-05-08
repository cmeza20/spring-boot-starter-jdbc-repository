package com.cmeza.spring.jdbc.repository.processors.methods;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcFunction;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcFunctionExecutor;

import java.util.Map;

public class FunctionAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcFunction> {
    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcFunction annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put("name", propertiesResolver.resolveRequiredPlaceholders(annotation.name()));
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
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcFunction annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcFunctionExecutor(annotation);
    }

}
