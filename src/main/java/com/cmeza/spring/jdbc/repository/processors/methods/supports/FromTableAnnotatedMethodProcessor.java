package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcFromTable;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.mappers.FromTableDefinitionMapper;

import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.ALIAS;
import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.TABLE;

public class FromTableAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcFromTable> {

    private final FromTableDefinitionMapper fromTableDefinitionMapper = new FromTableDefinitionMapper();

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcFromTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

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
    }

    @Override
    protected Object bindDefinition(JdbcFromTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return fromTableDefinitionMapper.parseFromTableAnnotation(annotation);
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_FROM_TABLE;
    }
}
