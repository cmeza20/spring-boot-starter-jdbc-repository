package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcPaginationProperties;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcPaginationParser;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.managers.CountQueryManager;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.managers.JoinTablesManager;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcPaginationExecutor;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcPagination;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;

import java.util.Map;
import java.util.Objects;

public class PaginationAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcPagination, JdbcPaginationProperties> {

    public PaginationAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcPaginationExecutor(annotation);
    }

    @Override
    protected JdbcPaginationProperties dslLocator(JdbcPagination annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcPaginationProperties jdbcPaginationProperties = dslProperties.getPagination();
        if (Objects.isNull(jdbcPaginationProperties)) {
            jdbcPaginationProperties = new JdbcPaginationProperties();
        }

        //Count Query
        CountQueryManager countQueryManager = new CountQueryManager(propertiesResolver, jdbcPaginationProperties.getCountQuery());
        countQueryManager.process(classMetadata, methodMetadata);

        //Join tables
        JoinTablesManager joinTablesManager = new JoinTablesManager(propertiesResolver, jdbcPaginationProperties.getJoinTables());
        joinTablesManager.process(classMetadata, methodMetadata);

        return jdbcPaginationProperties;
    }

    @Override
    protected void dslParser(JdbcPagination annotation, JdbcPaginationProperties dsl) {
        PARSER.getParser(JdbcPaginationParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcPaginationProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setTable(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getTable(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcPagination.class, JdbcConstants.TABLE)));
        dslProperty.setAlias(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getAlias(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcPagination.class, JdbcConstants.ALIAS)));
        dslProperty.setWhere(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getWhere(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcPagination.class, JdbcConstants.WHERE)));

        if (dslProperty.getSchema().isEmpty()) {
            dslProperty.setSchema(jdbcRepository.schema());
        } else {
            dslProperty.setSchema(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getSchema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcPagination.class, JdbcConstants.SCHEMA)));
        }
        if (dslProperty.getCatalog().isEmpty()) {
            dslProperty.setCatalog(jdbcRepository.catalog());
        } else {
            dslProperty.setCatalog(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getCatalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcPagination.class, JdbcConstants.CATALOG)));
        }

        NamingStrategy namingStrategy = this.extractNamingStrategy(dslProperty.getColumnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            dslProperty.setColumns(this.executeNamingStrategy(dslProperty.getColumns(), namingStrategy));
            dslProperty.setOrderBy(this.executeNamingStrategy(dslProperty.getOrderBy(), namingStrategy));
            dslProperty.setGroupBy(this.executeNamingStrategy(dslProperty.getGroupBy(), namingStrategy));
        }
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcPaginationProperties dslProperty) {
        //updateValues
    }

}
