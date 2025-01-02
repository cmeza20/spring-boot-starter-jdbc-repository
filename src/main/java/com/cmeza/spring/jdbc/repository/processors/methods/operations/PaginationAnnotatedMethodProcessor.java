package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcPagination;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcPaginationExecutor;

import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.*;

public class PaginationAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcPagination> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
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
            annotationValues.put(COLUMNS, this.executeNamingStrategy(annotation.columns(), namingStrategy));
            annotationValues.put(ORDER_BY, this.executeNamingStrategy(annotation.orderBy(), namingStrategy));
            annotationValues.put(GROUP_BY, this.executeNamingStrategy(annotation.groupBy(), namingStrategy));
        }
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcPaginationExecutor(annotation);
    }

}
