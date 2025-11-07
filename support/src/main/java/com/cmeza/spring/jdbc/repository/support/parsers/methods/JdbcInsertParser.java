package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcInsert;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcInsertProperties;
import org.apache.commons.lang3.StringUtils;

public class JdbcInsertParser implements IParser<JdbcInsert, JdbcInsertProperties> {
    @Override
    public void parse(JdbcInsert annotation, JdbcInsertProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.table())) {
            dslProperty.setTable(annotation.table());
        }
        if (StringUtils.isNotEmpty(annotation.catalog())) {
            dslProperty.setCatalog(annotation.catalog());
        }
        if (StringUtils.isNotEmpty(annotation.schema())) {
            dslProperty.setSchema(annotation.schema());
        }
        if (annotation.columns().length > 0) {
            dslProperty.setColumns(annotation.columns());
        }
        if (annotation.generatedKeyColumns().length > 0) {
            dslProperty.setGeneratedKeyColumns(annotation.generatedKeyColumns());
        }
        if (!annotation.accessTableColumnMetaData()) {
            dslProperty.setAccessTableColumnMetaData(false);
        }
        if (annotation.overrideIncludeSynonymsDefault()) {
            dslProperty.setOverrideIncludeSynonymsDefault(true);
        }
        if (annotation.loggable()) {
            dslProperty.setLoggable(true);
        }
        if (!annotation.columnsNamingStrategy().equals(NoOpNamingStrategy.class)) {
            dslProperty.setColumnsNamingStrategy(annotation.columnsNamingStrategy());
        }
    }
}
