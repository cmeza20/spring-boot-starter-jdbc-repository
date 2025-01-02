package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcInsert;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcInsertExecutor;

import java.util.Map;

public class InsertAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcInsert> {
    private static final String TABLE = "table";
    private static final String COLUMNS = "columns";
    private static final String GENERATED_KEY_COLUMNS = "generatedKeyColumns";

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcInsert annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(annotation.table()));

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

        NamingStrategy namingStrategy = this.extractNamingStrategy(annotation.columnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            annotationValues.put(COLUMNS, this.executeNamingStrategy(annotation.columns(), namingStrategy));
            annotationValues.put(GENERATED_KEY_COLUMNS, this.executeNamingStrategy(annotation.generatedKeyColumns(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcInsert annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcInsertExecutor(annotation);
    }

}
