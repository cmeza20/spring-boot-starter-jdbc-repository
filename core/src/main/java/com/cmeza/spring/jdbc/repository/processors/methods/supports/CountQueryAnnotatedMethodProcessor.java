package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcCountQuery;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcQueryParser;

import java.util.Map;

import static com.cmeza.spring.jdbc.repository.processors.methods.operations.QueryAnnotatedMethodProcessor.*;

public class CountQueryAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcCountQuery> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcCountQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {

        if (annotation.schema().isEmpty()) {
            annotationValues.put(SCHEMA, jdbcRepository.schema());
        } else {
            annotationValues.put(SCHEMA, propertiesResolver.resolveRequiredPlaceholders(annotation.schema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCountQuery.class, JdbcConstants.SCHEMA)));
        }
        if (annotation.catalog().isEmpty()) {
            annotationValues.put(CATALOG, jdbcRepository.catalog());
        } else {
            annotationValues.put(CATALOG, propertiesResolver.resolveRequiredPlaceholders(annotation.catalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCountQuery.class, JdbcConstants.CATALOG)));
        }

        annotationValues.put(TABLE, propertiesResolver.resolveRequiredPlaceholders(annotation.table(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCountQuery.class, JdbcConstants.TABLE)));
        annotationValues.put(ALIAS, propertiesResolver.resolveRequiredPlaceholders(annotation.alias(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCountQuery.class, JdbcConstants.ALIAS)));
        annotationValues.put(WHERE, propertiesResolver.resolveRequiredPlaceholders(annotation.where(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcCountQuery.class, JdbcConstants.WHERE)));

        NamingStrategy namingStrategy = this.extractNamingStrategy(annotation.columnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            annotationValues.put(COLUMNS, this.executeNamingStrategy(annotation.columns(), namingStrategy));
            annotationValues.put(GROUP_BY, this.executeNamingStrategy(annotation.groupBy(), namingStrategy));
        }
    }

    @Override
    protected Object bindDefinition(JdbcCountQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcSelectFactory selectBuilder = PARSER.getParser(JdbcQueryParser.class).parseFromCountQueryAnnotation(annotation);
        return selectBuilder.generateDefinition();
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_COUNT_QUERY;
    }
}
