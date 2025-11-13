package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcInsertExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcInsert;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcInsertParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcInsertProperties;

import java.util.Map;
import java.util.Objects;

public class InsertAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcInsert, JdbcInsertProperties> {

    public InsertAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcInsert annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcInsertExecutor(annotation);
    }

    @Override
    protected JdbcInsertProperties dslLocator(JdbcInsert annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcInsertProperties jdbcInsertProperties = dslProperties.getInsert();
        if (Objects.isNull(jdbcInsertProperties)) {
            jdbcInsertProperties = new JdbcInsertProperties();
        }

        return jdbcInsertProperties;
    }

    @Override
    protected void dslParser(JdbcInsert annotation, JdbcInsertProperties dsl) {
        PARSER.getParser(JdbcInsertParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcInsertProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setTable(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getTable(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcInsert.class, JdbcConstants.TABLE)));

        if (dslProperty.getSchema().isEmpty()) {
            dslProperty.setSchema(jdbcRepository.schema());
        } else {
            dslProperty.setSchema(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getSchema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcInsert.class, JdbcConstants.SCHEMA)));
        }
        if (dslProperty.getCatalog().isEmpty()) {
            dslProperty.setCatalog(jdbcRepository.catalog());
        } else {
            dslProperty.setCatalog(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getCatalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcInsert.class, JdbcConstants.CATALOG)));
        }

        NamingStrategy namingStrategy = this.extractNamingStrategy(dslProperty.getColumnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            dslProperty.setColumns(this.executeNamingStrategy(dslProperty.getColumns(), namingStrategy));
            dslProperty.setGeneratedKeyColumns(this.executeNamingStrategy(dslProperty.getGeneratedKeyColumns(), namingStrategy));
        }
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcInsertProperties dslProperty) {
        //updateValues
    }

}
