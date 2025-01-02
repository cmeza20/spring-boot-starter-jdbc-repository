package com.cmeza.spring.jdbc.repository.repositories.mappers;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcFromTable;
import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.definitions.TableDefinition;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.lang3.StringUtils;

public class FromTableDefinitionMapper {

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
