package com.cmeza.spring.jdbc.repository.support.parsers.supports;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcJoinTableProperties;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Join;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.Objects;

public class JdbcJoinTableParser implements IParser<JdbcJoinTable, JdbcJoinTableProperties> {
    @Override
    public void parse(JdbcJoinTable annotation, JdbcJoinTableProperties dslProperty) {
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
        if (StringUtils.isNotEmpty(annotation.on())) {
            dslProperty.setOn(annotation.on());
        }
        if (!annotation.join().equals(JdbcJoinTable.Join.INNER)) {
            dslProperty.setJoin(annotation.join());
        }
        if (!annotation.position().equals(JdbcJoinTable.JoinPosition.NORMAL)) {
            dslProperty.setPosition(annotation.position());
        }
    }

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
