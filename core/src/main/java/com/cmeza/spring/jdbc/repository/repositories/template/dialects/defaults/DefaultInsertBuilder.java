package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcInsertBuilder;
import org.slf4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

public class DefaultInsertBuilder extends AbstractJdbcBuilder<JdbcInsertBuilder> implements JdbcInsertBuilder {

    private final String tableName;
    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;
    private String catalog;
    private String schema;
    private String[] columns;
    private String[] generatedKeyColumns;
    private boolean accessTableColumnMetaData;
    private boolean overrideIncludeSynonymsDefault;

    public DefaultInsertBuilder(String tableName, Impl impl) {
        super(impl);
        this.tableName = tableName;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    @Override
    public int execute() {
        this.parameterSourcesRequired();
        return execute(() -> generateSimpleJdbcInsert().execute(getParameterSources()[0]));
    }

    @Override
    public int[] executeBatch() {
        this.parameterSourcesRequired();
        return execute(() -> generateSimpleJdbcInsert().executeBatch(getParameterSources()));
    }

    @Override
    public KeyHolder executeAndReturnKeyHolder() {
        this.parameterSourcesRequired();
        return execute(() -> generateSimpleJdbcInsert().executeAndReturnKeyHolder(getParameterSources()[0]));
    }

    @Override
    public JdbcInsertBuilder withSchema(String schema) {
        if (Objects.nonNull(schema) && !schema.isEmpty()) {
            this.schema = schema;
        }
        return this;
    }

    @Override
    public JdbcInsertBuilder withCatalog(String catalog) {
        if (Objects.nonNull(catalog) && !catalog.isEmpty()) {
            this.catalog = catalog;
        }
        return this;
    }

    @Override
    public JdbcInsertBuilder withColumns(String... columns) {
        if (Objects.nonNull(columns) && columns.length > 0) {
            this.columns = columns;
        }
        return this;
    }

    @Override
    public JdbcInsertBuilder withGeneratedKeyColumns(String... generatedKeyColumns) {
        if (Objects.nonNull(generatedKeyColumns) && generatedKeyColumns.length > 0) {
            this.generatedKeyColumns = generatedKeyColumns;
        }
        return this;
    }

    @Override
    public JdbcInsertBuilder withAccessTableColumnMetaData(boolean value) {
        this.accessTableColumnMetaData = value;
        return this;
    }

    @Override
    public JdbcInsertBuilder withOverrideIncludeSynonymsDefault(boolean value) {
        this.overrideIncludeSynonymsDefault = value;
        return this;
    }

    @Override
    public void printExtras(Logger logger) {
        if (Objects.nonNull(catalog) && !catalog.isEmpty()) {
            logger.info("| Catalog: {}", catalog);
        }
        if (Objects.nonNull(schema) && !schema.isEmpty()) {
            logger.info("| Schema: {}", schema);
        }
        logger.info("| Table name: {}", tableName);
        if (Objects.nonNull(columns) && columns.length > 0) {
            logger.info("| Columns: {}", (Object) columns);
        }
        if (Objects.nonNull(generatedKeyColumns) && generatedKeyColumns.length > 0) {
            logger.info("| GeneratedKeyColumns: {}", (Object) generatedKeyColumns);
        }
        logger.info("| AccessTableColumnMetaData: {}", accessTableColumnMetaData);
        logger.info("| OverrideIncludeSynonymsDefault: {}", overrideIncludeSynonymsDefault);
    }

    private SimpleJdbcInsert generateSimpleJdbcInsert() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcRepositoryTemplate.getJdbcTemplate());
        simpleJdbcInsert.withTableName(tableName);
        if (Objects.nonNull(schema)) {
            simpleJdbcInsert.withSchemaName(schema);
        }
        if (Objects.nonNull(catalog)) {
            simpleJdbcInsert.withCatalogName(catalog);
        }
        if (Objects.nonNull(columns)) {
            simpleJdbcInsert.usingColumns(columns);
        }
        if (Objects.nonNull(generatedKeyColumns)) {
            simpleJdbcInsert.usingGeneratedKeyColumns(generatedKeyColumns);
        }

        simpleJdbcInsert.setAccessTableColumnMetaData(accessTableColumnMetaData);
        simpleJdbcInsert.setOverrideIncludeSynonymsDefault(overrideIncludeSynonymsDefault);
        return simpleJdbcInsert;
    }
}
