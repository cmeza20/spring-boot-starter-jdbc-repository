package com.cmeza.spring.jdbc.repository.processors.methods;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcPagination;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcPaginationExecutor;

import java.util.Map;

public class PaginationAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcPagination> {
    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put("value", propertiesResolver.resolveRequiredPlaceholders(annotation.value()));
        annotationValues.put("countQuery", propertiesResolver.resolveRequiredPlaceholders(annotation.countQuery()));
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcPaginationExecutor(annotation);
    }

}
