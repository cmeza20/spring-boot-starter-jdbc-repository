package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcFromTable;
import com.cmeza.spring.jdbc.repository.support.parsers.supports.JdbcFromTableParser;

import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.ALIAS;
import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.TABLE;

public class FromTableAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcFromTable> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcFromTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

        if (annotation.schema().isEmpty()) {
            annotationValues.put(SCHEMA, jdbcRepository.schema());
        } else {
            annotationValues.put(SCHEMA, propertiesResolver.resolveRequiredPlaceholders(annotation.schema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcFromTable.class, JdbcConstants.SCHEMA)));
        }
        if (annotation.catalog().isEmpty()) {
            annotationValues.put(CATALOG, jdbcRepository.catalog());
        } else {
            annotationValues.put(CATALOG, propertiesResolver.resolveRequiredPlaceholders(annotation.catalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcFromTable.class, JdbcConstants.CATALOG)));
        }

        annotationValues.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(annotation.table(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcFromTable.class, JdbcConstants.TABLE)));
        annotationValues.put(ALIAS, propertiesResolver.resolveRequiredPlaceholders(annotation.alias(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcFromTable.class, JdbcConstants.ALIAS)));
    }

    @Override
    protected Object bindDefinition(JdbcFromTable annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return PARSER.getParser(JdbcFromTableParser.class).parseFromTableAnnotation(annotation);
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_FROM_TABLE;
    }
}
