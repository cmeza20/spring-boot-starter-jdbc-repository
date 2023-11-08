package com.cmeza.spring.jdbc.repository.processors.methods;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcProcedureExecutor;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcNamedParameterUtils;

import java.util.Map;

public class ProcedureAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcProcedure> {
    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcProcedure annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
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

        NamingStrategy namingStrategy = this.extractNamingStrategy(annotation.parametersNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            annotationValues.put("inParameterNames", this.executeNamingStrategy(annotation.inParameterNames(), namingStrategy));
            annotationValues.put("rowMapperParameterName", namingStrategy.parse(annotation.rowMapperParameterName()));
            annotationValues.put("outParameters", JdbcNamedParameterUtils.extractOutParameters(annotation.outParameters(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcProcedure annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcProcedureExecutor(annotation);
    }

}
