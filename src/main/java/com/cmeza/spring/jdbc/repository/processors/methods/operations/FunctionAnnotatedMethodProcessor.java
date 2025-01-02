package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcFunction;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcFunctionExecutor;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcNamedParameterUtils;

import java.util.Map;

public class FunctionAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcFunction> {

    private static final String IN_PARAMETER_NAMES = "inParameterNames";
    private static final String ROWMAPPER_PARAMETER_NAME = "rowMapperParameterName";
    private static final String OUT_PARAMETERS = "outParameters";

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcFunction annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(NAME, propertiesResolver.resolveRequiredPlaceholders(annotation.name()));
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
            annotationValues.put(IN_PARAMETER_NAMES, this.executeNamingStrategy(annotation.inParameterNames(), namingStrategy));
            annotationValues.put(ROWMAPPER_PARAMETER_NAME, namingStrategy.parse(annotation.rowMapperParameterName()));
            annotationValues.put(OUT_PARAMETERS, JdbcNamedParameterUtils.extractOutParameters(annotation.outParameters(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcFunction annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcFunctionExecutor(annotation);
    }

}
