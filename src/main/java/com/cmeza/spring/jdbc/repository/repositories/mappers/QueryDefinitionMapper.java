package com.cmeza.spring.jdbc.repository.repositories.mappers;

import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.definitions.QueryDefinition;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class QueryDefinitionMapper {

    private final JoinTableDefinitionMapper joinTableDefinitionMapper = new JoinTableDefinitionMapper();

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
            queryDefinition.joinTables(select.getJoins().stream().map(joinTableDefinitionMapper::parseFromJoin).toArray(JoinTableDefinition[]::new));
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

}
