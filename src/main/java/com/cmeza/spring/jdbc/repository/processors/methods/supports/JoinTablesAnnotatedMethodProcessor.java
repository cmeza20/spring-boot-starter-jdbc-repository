package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.groups.JdbcJoinTables;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.mappers.JoinTableDefinitionMapper;

import java.util.*;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.ALIAS;
import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.TABLE;
import static com.cmeza.spring.jdbc.repository.processors.methods.supports.JoinTableAnnotatedMethodProcessor.*;

public class JoinTablesAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcJoinTables> {

    public static final String POSITION = "position";
    private final JoinTableDefinitionMapper joinTableDefinitionMapper = new JoinTableDefinitionMapper();

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcJoinTables annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

        List<Map<String, Object>> value = new LinkedList<>();
        for (JdbcJoinTable jdbcJoinTable : annotation.value()) {
            Map<String, Object> annotationValue = new HashMap<>();
            if (jdbcJoinTable.schema().isEmpty()) {
                annotationValue.put(SCHEMA, jdbcRepository.schema());
            } else {
                annotationValue.put(SCHEMA, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.schema()));
            }
            if (jdbcJoinTable.catalog().isEmpty()) {
                annotationValue.put(CATALOG, jdbcRepository.catalog());
            } else {
                annotationValue.put(CATALOG, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.catalog()));
            }

            annotationValue.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.table()));
            annotationValue.put(ALIAS, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.alias()));
            annotationValue.put(ON, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.on()));
            annotationValue.put(JOIN, jdbcJoinTable.join());
            annotationValue.put(POSITION, jdbcJoinTable.position());
            value.add(annotationValue);

        }
        annotationValues.put("value", value.toArray());
    }

    @Override
    protected Object bindDefinition(JdbcJoinTables annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JoinTableDefinition[] tables = new JoinTableDefinition[methodMetadata.getMethod().getAnnotationsByType(JdbcJoinTable.class).length];

        int position = 0;
        for (JdbcJoinTable tableAnnotation : annotation.value()) {
            tables[position] = joinTableDefinitionMapper.parseFromJoinTableAnnotation(tableAnnotation);
            position++;
        }
        return tables;
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_JOIN_TABLES;
    }
}
