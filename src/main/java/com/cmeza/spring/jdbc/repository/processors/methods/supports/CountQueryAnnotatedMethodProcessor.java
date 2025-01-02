package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcCountQuery;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.mappers.SelectBuilderMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;

import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.*;

public class CountQueryAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcCountQuery> {

    private final SelectBuilderMapper selectBuilderMapper = new SelectBuilderMapper();

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcCountQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

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

        annotationValues.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(annotation.table()));
        annotationValues.put(ALIAS, propertiesResolver.resolveRequiredPlaceholders(annotation.alias()));
        annotationValues.put(WHERE, propertiesResolver.resolveRequiredPlaceholders(annotation.where()));

        NamingStrategy namingStrategy = this.extractNamingStrategy(annotation.columnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            annotationValues.put(COLUMNS, this.executeNamingStrategy(annotation.columns(), namingStrategy));
            annotationValues.put(GROUP_BY, this.executeNamingStrategy(annotation.groupBy(), namingStrategy));
        }
    }

    @Override
    protected Object bindDefinition(JdbcCountQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcSelectFactory selectBuilder = selectBuilderMapper.parseFromCountQueryAnnotation(annotation);
        return selectBuilder.generateDefinition();
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_COUNT_QUERY;
    }
}
