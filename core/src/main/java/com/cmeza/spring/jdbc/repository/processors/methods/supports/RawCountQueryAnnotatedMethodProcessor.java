package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcRawCountQuery;
import com.cmeza.spring.jdbc.repository.support.exceptions.JdbcException;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.support.factories.defaults.DefaultSelectFactory;

import java.util.Map;

public class RawCountQueryAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcRawCountQuery> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcRawCountQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(VALUE, propertiesResolver.resolveRequiredPlaceholders(annotation.value(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcRawCountQuery.class, JdbcConstants.VALUE)));
    }

    @Override
    protected Object bindDefinition(JdbcRawCountQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        try {
            JdbcSelectFactory selectMaker = new DefaultSelectFactory(VALUE).readQuery(annotation.value());
            return selectMaker.generateDefinition();
        } catch (Exception e) {
            throw new JdbcException(e);
        }
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_COUNT_QUERY;
    }
}
