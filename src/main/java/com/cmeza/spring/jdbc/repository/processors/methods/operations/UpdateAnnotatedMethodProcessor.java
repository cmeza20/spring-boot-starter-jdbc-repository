package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcUpdateExecutor;

import java.util.Map;

public class UpdateAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcUpdate> {
    private static final String KEY_COLUMN_NAMES = "keyColumnNames";
    public static final String TABLE = "table";
    public static final String ALIAS = "alias";
    public static final String UPDATESETS = "updateSets";
    public static final String WHERE = "where";

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(annotation.table()));
        annotationValues.put(ALIAS, propertiesResolver.resolveRequiredPlaceholders(annotation.alias()));
        annotationValues.put(WHERE, propertiesResolver.resolveRequiredPlaceholders(annotation.where()));

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
            annotationValues.put(UPDATESETS, this.executeNamingStrategy(annotation.updateSets(), namingStrategy));
            annotationValues.put(KEY_COLUMN_NAMES, this.executeNamingStrategy(annotation.keyColumnNames(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcUpdateExecutor(annotation);
    }
}
