package com.cmeza.spring.jdbc.repository.repositories.mappers;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Objects;

public class JoinTableDefinitionMapper {

    public JoinTableDefinition parseFromJoinTableAnnotation(JdbcJoinTable annotation) {
        return JoinTableDefinition.builder()
                .catalog(annotation.catalog())
                .schema(annotation.schema())
                .table(annotation.table())
                .alias(annotation.alias())
                .on(annotation.on())
                .join(annotation.join())
                .position(annotation.position())
                .build();
    }

    public Join parseToJoin(JoinTableDefinition joinTableDefinition) {
        Table table = new Table(joinTableDefinition.getTable());
        if (StringUtils.isNotEmpty(joinTableDefinition.getCatalog())) {
            table.withDatabase(new Database(joinTableDefinition.getCatalog()));
        }
        if (StringUtils.isNotEmpty(joinTableDefinition.getSchema())) {
            table.withSchemaName(joinTableDefinition.getSchema());
        }
        table.setAlias(new Alias(joinTableDefinition.getAlias(), false));
        Join join = new Join().setFromItem(table);
        join.setInner(joinTableDefinition.getJoin().equals(JdbcJoinTable.Join.INNER));
        join.setLeft(joinTableDefinition.getJoin().equals(JdbcJoinTable.Join.LEFT));
        join.setRight(joinTableDefinition.getJoin().equals(JdbcJoinTable.Join.RIGHT));
        join.setFull(joinTableDefinition.getJoin().equals(JdbcJoinTable.Join.FULL));

        if (StringUtils.isNotEmpty(joinTableDefinition.getOn())) {
            join.setOnExpressions(Collections.singletonList(new Column(joinTableDefinition.getOn())));
        }
        return join;
    }

    public JoinTableDefinition parseFromJoin(Join join) {
        JoinTableDefinition.JoinTableDefinitionBuilder<?, ?> builder = JoinTableDefinition.builder();
        Table fromItem = (Table) join.getFromItem();
        builder.table(fromItem.getName());

        if (Objects.nonNull(fromItem.getDatabase()) && StringUtils.isNotEmpty(fromItem.getDatabase().getDatabaseName())) {
            builder.catalog(fromItem.getDatabase().getDatabaseName());
        }
        if (StringUtils.isNotEmpty(fromItem.getSchemaName())) {
            builder.schema(fromItem.getSchemaName());
        }
        if (Objects.nonNull(fromItem.getAlias())) {
            builder.alias(fromItem.getAlias().toString());
        }
        if (Objects.nonNull(join.getOnExpressions()) && !join.getOnExpressions().isEmpty()) {
            builder.on(StringUtils.join(join.getOnExpressions()));
        }
        if (join.isInner()) {
            builder.join(JdbcJoinTable.Join.INNER);
        } else if (join.isLeft()) {
            builder.join(JdbcJoinTable.Join.LEFT);
        } else if (join.isRight()) {
            builder.join(JdbcJoinTable.Join.RIGHT);
        } else {
            builder.join(JdbcJoinTable.Join.FULL);
        }
        return builder.build();
    }
}
