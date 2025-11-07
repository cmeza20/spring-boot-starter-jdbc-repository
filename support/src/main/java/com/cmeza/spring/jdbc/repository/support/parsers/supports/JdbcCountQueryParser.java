package com.cmeza.spring.jdbc.repository.support.parsers.supports;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcCountQuery;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcCountQueryProperties;
import org.apache.commons.lang3.StringUtils;

public class JdbcCountQueryParser implements IParser<JdbcCountQuery, JdbcCountQueryProperties> {
    @Override
    public void parse(JdbcCountQuery annotation, JdbcCountQueryProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.schema())) {
            dslProperty.setSchema(annotation.schema());
        }
        if (StringUtils.isNotEmpty(annotation.catalog())) {
            dslProperty.setCatalog(annotation.catalog());
        }
        if (annotation.columns().length > 0) {
            dslProperty.setColumns(annotation.columns());
        }
        if (StringUtils.isNotEmpty(annotation.table())) {
            dslProperty.setTable(annotation.table());
        }
        if (!annotation.alias().equals("t")) {
            dslProperty.setAlias(annotation.alias());
        }
        if (StringUtils.isNotEmpty(annotation.where())) {
            dslProperty.setWhere(annotation.where());
        }
        if (annotation.groupBy().length > 0) {
            dslProperty.setGroupBy(annotation.groupBy());
        }
        if (!annotation.columnsNamingStrategy().equals(NoOpNamingStrategy.class)) {
            dslProperty.setColumnsNamingStrategy(annotation.columnsNamingStrategy());
        }
    }
}
