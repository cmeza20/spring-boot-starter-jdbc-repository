package com.cmeza.spring.jdbc.repository.processors.methods;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcUpdateExecutor;

import java.util.Map;

public class UpdateAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcUpdate> {
    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put("value", propertiesResolver.resolveRequiredPlaceholders(annotation.value()));

        NamingStrategy namingStrategy = this.extractNamingStrategy(annotation.columnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            annotationValues.put("keyColumnNames", this.executeNamingStrategy(annotation.keyColumnNames(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcUpdateExecutor(annotation);
    }
}
