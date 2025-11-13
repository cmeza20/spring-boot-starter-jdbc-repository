package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcUpdateExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcRawUpdateParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawUpdateProperties;

import java.util.Map;
import java.util.Objects;

public class RawUpdateAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcRawUpdate, JdbcRawUpdateProperties> {

    public RawUpdateAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcRawUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcUpdateExecutor(annotation);
    }

    @Override
    protected JdbcRawUpdateProperties dslLocator(JdbcRawUpdate annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcRawUpdateProperties jdbcRawUpdateProperties = dslProperties.getRawUpdate();
        if (Objects.isNull(jdbcRawUpdateProperties)) {
            jdbcRawUpdateProperties = new JdbcRawUpdateProperties();
        }

        return jdbcRawUpdateProperties;
    }

    @Override
    protected void dslParser(JdbcRawUpdate annotation, JdbcRawUpdateProperties dsl) {
        PARSER.getParser(JdbcRawUpdateParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcRawUpdateProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setValue(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getValue(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcRawUpdate.class, JdbcConstants.VALUE)));

        NamingStrategy namingStrategy = this.extractNamingStrategy(dslProperty.getColumnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            dslProperty.setKeyColumnNames(this.executeNamingStrategy(dslProperty.getKeyColumnNames(), namingStrategy));
        }
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcRawUpdateProperties dslProperty) {
        //updateValues
    }
}
