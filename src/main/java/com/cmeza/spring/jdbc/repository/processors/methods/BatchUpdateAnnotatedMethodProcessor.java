package com.cmeza.spring.jdbc.repository.processors.methods;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcBatchUpdate;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcBatchUpdateExecutor;

import java.util.Map;

public class BatchUpdateAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcBatchUpdate> {
    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcBatchUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put("value", propertiesResolver.resolveRequiredPlaceholders(annotation.value()));
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcBatchUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcBatchUpdateExecutor(annotation);
    }
}
