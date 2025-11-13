package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcQueryExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawQuery;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcRawQueryParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawQueryProperties;

import java.util.Map;
import java.util.Objects;

public class RawQueryAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcRawQuery, JdbcRawQueryProperties> {

    public RawQueryAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcRawQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcQueryExecutor(annotation);
    }

    @Override
    protected JdbcRawQueryProperties dslLocator(JdbcRawQuery annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcRawQueryProperties jdbcRawQueryProperties = dslProperties.getRawQuery();
        if (Objects.isNull(jdbcRawQueryProperties)) {
            jdbcRawQueryProperties = new JdbcRawQueryProperties();
        }

        return jdbcRawQueryProperties;
    }

    @Override
    protected void dslParser(JdbcRawQuery annotation, JdbcRawQueryProperties dsl) {
        PARSER.getParser(JdbcRawQueryParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcRawQueryProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setValue(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getValue(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcRawQuery.class, JdbcConstants.VALUE)));
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcRawQueryProperties dslProperty) {
        //updateValues
    }

}
