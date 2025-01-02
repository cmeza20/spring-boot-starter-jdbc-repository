package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcRawPagination;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcPaginationExecutor;

import java.util.Map;

public class RawPaginationAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcRawPagination> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcRawPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(VALUE, propertiesResolver.resolveRequiredPlaceholders(annotation.value()));
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcRawPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcPaginationExecutor(annotation);
    }

}
