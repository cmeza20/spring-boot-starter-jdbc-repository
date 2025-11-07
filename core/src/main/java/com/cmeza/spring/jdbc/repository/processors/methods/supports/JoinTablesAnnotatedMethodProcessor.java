package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.groups.JdbcJoinTables;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.support.parsers.supports.JdbcJoinTableParser;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.ALIAS;
import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.TABLE;
import static com.cmeza.spring.jdbc.repository.processors.methods.supports.JoinTableAnnotatedMethodProcessor.JOIN;
import static com.cmeza.spring.jdbc.repository.processors.methods.supports.JoinTableAnnotatedMethodProcessor.ON;

public class JoinTablesAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcJoinTables> {

    public static final String POSITION = "position";

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcJoinTables annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

        List<Map<String, Object>> value = new LinkedList<>();
        for (JdbcJoinTable jdbcJoinTable : annotation.value()) {
            Map<String, Object> annotationValue = new HashMap<>();
            if (jdbcJoinTable.schema().isEmpty()) {
                annotationValue.put(SCHEMA, jdbcRepository.schema());
            } else {
                annotationValue.put(SCHEMA, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.schema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTables.class, JdbcConstants.SCHEMA)));
            }
            if (jdbcJoinTable.catalog().isEmpty()) {
                annotationValue.put(CATALOG, jdbcRepository.catalog());
            } else {
                annotationValue.put(CATALOG, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.catalog(),  JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTables.class, JdbcConstants.CATALOG)));
            }

            annotationValue.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.table(),  JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTables.class, JdbcConstants.TABLE)));
            annotationValue.put(ALIAS, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.alias(),   JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTables.class, JdbcConstants.ALIAS)));
            annotationValue.put(ON, propertiesResolver.resolveRequiredPlaceholders(jdbcJoinTable.on(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTables.class, JdbcConstants.ON)));
            annotationValue.put(JOIN, jdbcJoinTable.join());
            annotationValue.put(POSITION, jdbcJoinTable.position());
            value.add(annotationValue);

        }
        annotationValues.put("value", value.toArray());
    }

    @Override
    protected Object bindDefinition(JdbcJoinTables annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JoinTableDefinition[] tables = methodMetadata.getAttribute(tagUniqueSupport(), JoinTableDefinition[].class, new JoinTableDefinition[0]);

        if (annotation.value().length > 0) {

            JoinTableDefinition[] currentTables = new JoinTableDefinition[annotation.value().length];
            int position = 0;
            for (JdbcJoinTable tableAnnotation : annotation.value()) {
                currentTables[position] = PARSER.getParser(JdbcJoinTableParser.class).parseFromJoinTableAnnotation(tableAnnotation);
                position++;
            }

            tables = ArrayUtils.addAll(tables, currentTables);
        }

        return tables;
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_JOIN_TABLES;
    }
}
