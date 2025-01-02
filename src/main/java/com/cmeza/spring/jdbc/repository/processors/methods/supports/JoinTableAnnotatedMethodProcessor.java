package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.mappers.JoinTableDefinitionMapper;

import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.ALIAS;
import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.TABLE;

public class JoinTableAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcJoinTable> {

    public static final String ON = "on";
    public static final String JOIN = "join";
    private final JoinTableDefinitionMapper joinTableDefinitionMapper = new JoinTableDefinitionMapper();

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcJoinTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

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
        annotationValues.put(ON, propertiesResolver.resolveRequiredPlaceholders(annotation.on()));
    }

    @Override
    protected Object bindDefinition(JdbcJoinTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JoinTableDefinition[]{joinTableDefinitionMapper.parseFromJoinTableAnnotation(annotation)};
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_JOIN_TABLES;
    }
}
