package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcRawCountQuery;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.exceptions.JdbcException;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.factories.DefaultSelectFactory;

import java.util.Map;

public class RawCountQueryAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcRawCountQuery> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcRawCountQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        annotationValues.put(VALUE, propertiesResolver.resolveRequiredPlaceholders(annotation.value()));
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
