package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcRawUpdate;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcUpdateExecutor;

import java.util.Map;

public class RawUpdateAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcRawUpdate> {
    private static final String KEY_COLUMN_NAMES = "keyColumnNames";

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcRawUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(VALUE, propertiesResolver.resolveRequiredPlaceholders(annotation.value()));

        NamingStrategy namingStrategy = this.extractNamingStrategy(annotation.columnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            annotationValues.put(KEY_COLUMN_NAMES, this.executeNamingStrategy(annotation.keyColumnNames(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcRawUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcUpdateExecutor(annotation);
    }
}
