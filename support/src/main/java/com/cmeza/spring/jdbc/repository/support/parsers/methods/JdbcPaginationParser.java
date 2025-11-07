package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcPagination;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcPaginationProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

public class JdbcPaginationParser implements IParser<JdbcPagination, JdbcPaginationProperties> {
    @Override
    public void parse(JdbcPagination annotation, JdbcPaginationProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.schema())) {
            dslProperty.setSchema(annotation.schema());
        }
        if (StringUtils.isNotEmpty(annotation.catalog())) {
            dslProperty.setCatalog(annotation.catalog());
        }
        if ((annotation.columns().length == 1 && !ArrayUtils.contains(annotation.columns(), "*")) || annotation.columns().length > 1) {
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
        if (annotation.orderBy().length > 0) {
            dslProperty.setOrderBy(annotation.orderBy());
        }
        if (annotation.groupBy().length > 0) {
            dslProperty.setGroupBy(annotation.groupBy());
        }
        if (!annotation.mapper().equals(RowMapper.class)) {
            dslProperty.setMapper(annotation.mapper());
        }
        if (annotation.loggable()) {
            dslProperty.setLoggable(true);
        }
        if (!annotation.columnsNamingStrategy().equals(NoOpNamingStrategy.class)) {
            dslProperty.setColumnsNamingStrategy(annotation.columnsNamingStrategy());
        }
    }
}
