package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.managers.FromTableManager;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.managers.JoinTablesManager;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcUpdateExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcUpdateParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcUpdateProperties;

import java.util.Map;
import java.util.Objects;

public class UpdateAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcUpdate, JdbcUpdateProperties> {
    public UpdateAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcUpdate annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcUpdateExecutor(annotation);
    }

    @Override
    protected JdbcUpdateProperties dslLocator(JdbcUpdate annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcUpdateProperties jdbcUpdateProperties = dslProperties.getUpdate();
        if (Objects.isNull(jdbcUpdateProperties)) {
            jdbcUpdateProperties = new JdbcUpdateProperties();
        }

        //Join tables
        JoinTablesManager joinTablesManager = new JoinTablesManager(propertiesResolver, jdbcUpdateProperties.getJoinTables());
        joinTablesManager.process(classMetadata, methodMetadata);

        //From table
        FromTableManager fromTableManager = new FromTableManager(propertiesResolver, jdbcUpdateProperties.getFromTable());
        fromTableManager.process(classMetadata, methodMetadata);

        return jdbcUpdateProperties;
    }

    @Override
    protected void dslParser(JdbcUpdate annotation, JdbcUpdateProperties dsl) {
        PARSER.getParser(JdbcUpdateParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcUpdateProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setTable(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getTable(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcUpdate.class, JdbcConstants.TABLE)));
        dslProperty.setAlias(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getAlias(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcUpdate.class, JdbcConstants.ALIAS)));
        dslProperty.setWhere(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getWhere(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcUpdate.class, JdbcConstants.WHERE)));

        if (dslProperty.getSchema().isEmpty()) {
            dslProperty.setSchema(jdbcRepository.schema());
        } else {
            dslProperty.setSchema(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getSchema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcUpdate.class, JdbcConstants.SCHEMA)));
        }
        if (dslProperty.getCatalog().isEmpty()) {
            dslProperty.setCatalog(jdbcRepository.catalog());
        } else {
            dslProperty.setCatalog(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getCatalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcUpdate.class, JdbcConstants.CATALOG)));
        }

        NamingStrategy namingStrategy = this.extractNamingStrategy(dslProperty.getColumnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            dslProperty.setUpdateSets(this.executeNamingStrategy(dslProperty.getUpdateSets(), namingStrategy));
            dslProperty.setKeyColumnNames(this.executeNamingStrategy(dslProperty.getKeyColumnNames(), namingStrategy));
        }
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcUpdateProperties dslProperty) {
        //updateValues
    }
}
