package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.factories.defaults.DefaultSelectFactory;
import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.parsers.Parser;
import com.cmeza.spring.jdbc.repository.support.parsers.supports.JdbcJoinTableParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcQuery;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcCountQuery;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.support.definitions.QueryDefinition;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcQueryProperties;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;

public class JdbcQueryParser implements IParser<JdbcQuery, JdbcQueryProperties> {
    private static final Parser PARSER = Parser.getInstance();

    @Override
    public void parse(JdbcQuery annotation, JdbcQueryProperties dslProperty) {
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

    public QueryDefinition parseFromSelect(PlainSelect select) {
        QueryDefinition.QueryDefinitionBuilder<?, ?> queryDefinition = QueryDefinition.builder();
        Table table = select.getFromItem(Table.class);
        if (Objects.nonNull(table)) {
            if (Objects.nonNull(table.getDatabase()) && StringUtils.isNotEmpty(table.getDatabase().getDatabaseName())) {
                queryDefinition.catalog(table.getDatabase().getDatabaseName());
            }
            if (StringUtils.isNotEmpty(table.getSchemaName())) {
                queryDefinition.schema(table.getSchemaName());
            }
            queryDefinition.table(table.getName());
            queryDefinition.alias(table.getAlias().toString());
        }

        queryDefinition.columns(select.getSelectItems().stream().map(SelectItem::toString).toArray(String[]::new));

        if (Objects.nonNull(select.getJoins())) {
            queryDefinition.joinTables(select.getJoins().stream().map(PARSER.getParser(JdbcJoinTableParser.class)::parseFromJoin).toArray(JoinTableDefinition[]::new));
        } else {
            queryDefinition.joinTables(new JoinTableDefinition[0]);
        }

        if (Objects.nonNull(select.getGroupBy())) {
            String[] groupBy = (String[]) select.getGroupBy().getGroupByExpressionList().stream().map(Object::toString).toArray(String[]::new);
            queryDefinition.groupBy(groupBy);
        } else {
            queryDefinition.groupBy(new String[0]);
        }

        if (Objects.nonNull(select.getOrderByElements())) {
            queryDefinition.orderBy(select.getOrderByElements().stream().map(OrderByElement::toString).toArray(String[]::new));
        } else {
            queryDefinition.orderBy(new String[0]);
        }

        if (Objects.nonNull(select.getWhere())) {
            queryDefinition.where(select.getWhere().toString());
        }


        return queryDefinition.build();
    }

    public JdbcSelectFactory parseFromCountQueryAnnotation(JdbcCountQuery annotation) {
        return new DefaultSelectFactory(annotation.table())
                .withSchema(annotation.schema())
                .withCatalog(annotation.catalog())
                .withColumns(annotation.columns())
                .withAlias(annotation.alias())
                .withWhere(annotation.where())
                .withGroupBy(annotation.groupBy());
    }
}
