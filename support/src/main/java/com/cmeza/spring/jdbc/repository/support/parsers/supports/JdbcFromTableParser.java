package com.cmeza.spring.jdbc.repository.support.parsers.supports;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcFromTable;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.support.definitions.TableDefinition;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcFromTableProperties;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;

public class JdbcFromTableParser implements IParser<JdbcFromTable, JdbcFromTableProperties> {
    @Override
    public void parse(JdbcFromTable annotation, JdbcFromTableProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.catalog())) {
            dslProperty.setCatalog(annotation.catalog());
        }
        if (StringUtils.isNotEmpty(annotation.schema())) {
            dslProperty.setSchema(annotation.schema());
        }
        if (StringUtils.isNotEmpty(annotation.table())) {
            dslProperty.setTable(annotation.table());
        }
        if (StringUtils.isNotEmpty(annotation.alias())) {
            dslProperty.setAlias(annotation.alias());
        }
    }

    public TableDefinition parseFromTableAnnotation(JdbcFromTable annotation) {
        return JoinTableDefinition.builder()
                .catalog(annotation.catalog())
                .schema(annotation.schema())
                .table(annotation.table())
                .alias(annotation.alias())
                .build();
    }

    public Table parseToTable(TableDefinition joinTableDefinition) {
        Table table = new Table(joinTableDefinition.getTable());
        if (StringUtils.isNotEmpty(joinTableDefinition.getCatalog())) {
            table.withDatabase(new Database(joinTableDefinition.getCatalog()));
        }
        if (StringUtils.isNotEmpty(joinTableDefinition.getSchema())) {
            table.withSchemaName(joinTableDefinition.getSchema());
        }
        table.setAlias(new Alias(joinTableDefinition.getAlias(), false));
        return table;
    }
}
