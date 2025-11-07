package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.managers.JoinTablesManager;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcQueryExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcQuery;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcQueryParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcQueryProperties;

import java.util.Map;
import java.util.Objects;

public class QueryAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcQuery, JdbcQueryProperties> {

    public static final String TABLE = "table";
    public static final String ALIAS = "alias";
    public static final String COLUMNS = "columns";
    public static final String WHERE = "where";
    public static final String GROUP_BY = "groupBy";
    public static final String ORDER_BY = "orderBy";

    public QueryAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcQuery annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcQueryExecutor(annotation);
    }

    @Override
    protected JdbcQueryProperties dslLocator(JdbcQuery annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcQueryProperties jdbcQueryProperties = dslProperties.getQuery();
        if (Objects.isNull(jdbcQueryProperties)) {
            jdbcQueryProperties = new JdbcQueryProperties();
        }

        //Join tables
        JoinTablesManager joinTablesManager = new JoinTablesManager(propertiesResolver, jdbcQueryProperties.getJoinTables());
        joinTablesManager.process(classMetadata, methodMetadata);

        return jdbcQueryProperties;
    }

    @Override
    protected void dslParser(JdbcQuery annotation, JdbcQueryProperties dsl) {
        PARSER.getParser(JdbcQueryParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcQueryProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setTable(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getTable(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcQuery.class, JdbcConstants.TABLE)));
        dslProperty.setAlias(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getAlias(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcQuery.class, JdbcConstants.ALIAS)));
        dslProperty.setWhere(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getWhere(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcQuery.class, JdbcConstants.WHERE)));

        if (dslProperty.getSchema().isEmpty()) {
            dslProperty.setSchema(jdbcRepository.schema());
        } else {
            dslProperty.setSchema(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getSchema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcQuery.class, JdbcConstants.SCHEMA)));
        }
        if (dslProperty.getCatalog().isEmpty()) {
            dslProperty.setCatalog(jdbcRepository.catalog());
        } else {
            dslProperty.setCatalog(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getCatalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcQuery.class, JdbcConstants.CATALOG)));
        }

        NamingStrategy namingStrategy = this.extractNamingStrategy(dslProperty.getColumnsNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            dslProperty.setColumns(this.executeNamingStrategy(dslProperty.getColumns(), namingStrategy));
            dslProperty.setOrderBy(this.executeNamingStrategy(dslProperty.getOrderBy(), namingStrategy));
            dslProperty.setGroupBy(this.executeNamingStrategy(dslProperty.getGroupBy(), namingStrategy));
        }
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcQueryProperties dslProperty) {
        //updateValues
    }

}
