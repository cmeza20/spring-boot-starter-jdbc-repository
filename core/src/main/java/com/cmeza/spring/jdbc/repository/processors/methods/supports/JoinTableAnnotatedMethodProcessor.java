package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.parsers.supports.JdbcJoinTableParser;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;

import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.ALIAS;
import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.TABLE;

public class JoinTableAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcJoinTable> {

    public static final String ON = "on";
    public static final String JOIN = "join";

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcJoinTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

        if (annotation.schema().isEmpty()) {
            annotationValues.put(SCHEMA, jdbcRepository.schema());
        } else {
            annotationValues.put(SCHEMA, propertiesResolver.resolveRequiredPlaceholders(annotation.schema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTable.class, JdbcConstants.SCHEMA)));
        }
        if (annotation.catalog().isEmpty()) {
            annotationValues.put(CATALOG, jdbcRepository.catalog());
        } else {
            annotationValues.put(CATALOG, propertiesResolver.resolveRequiredPlaceholders(annotation.catalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTable.class, JdbcConstants.CATALOG)));
        }

        annotationValues.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(annotation.table(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTable.class, JdbcConstants.TABLE)));
        annotationValues.put(ALIAS, propertiesResolver.resolveRequiredPlaceholders(annotation.alias(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTable.class, JdbcConstants.ALIAS)));
        annotationValues.put(ON, propertiesResolver.resolveRequiredPlaceholders(annotation.on(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcJoinTable.class, JdbcConstants.ON)));
    }

    @Override
    protected Object bindDefinition(JdbcJoinTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JoinTableDefinition[]{PARSER.getParser(JdbcJoinTableParser.class).parseFromJoinTableAnnotation(annotation)};
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_JOIN_TABLES;
    }
}
