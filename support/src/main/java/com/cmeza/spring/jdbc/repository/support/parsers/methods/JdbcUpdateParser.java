package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.support.definitions.UpdateDefinition;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.parsers.Parser;
import com.cmeza.spring.jdbc.repository.support.parsers.supports.JdbcJoinTableParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcUpdateProperties;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class JdbcUpdateParser implements IParser<JdbcUpdate, JdbcUpdateProperties> {
    private static final Parser PARSER = Parser.getInstance();

    @Override
    public void parse(JdbcUpdate annotation, JdbcUpdateProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.schema())) {
            dslProperty.setSchema(annotation.schema());
        }
        if (StringUtils.isNotEmpty(annotation.catalog())) {
            dslProperty.setCatalog(annotation.catalog());
        }
        if (Objects.nonNull(annotation.updateSets()) && annotation.updateSets().length > 0) {
            dslProperty.setUpdateSets(annotation.updateSets());
        }
        if (StringUtils.isNotEmpty(annotation.table())) {
            dslProperty.setTable(annotation.table());
        }
        if (StringUtils.isNotEmpty(annotation.alias())) {
            dslProperty.setAlias(annotation.alias());
        }
        if (StringUtils.isNotEmpty(annotation.where())) {
            dslProperty.setWhere(annotation.where());
        }
        if (annotation.keyColumnNames().length > 0) {
            dslProperty.setKeyColumnNames(annotation.keyColumnNames());
        }
        if (annotation.loggable()) {
            dslProperty.setLoggable(true);
        }
        if (!annotation.columnsNamingStrategy().equals(NoOpNamingStrategy.class)) {
            dslProperty.setColumnsNamingStrategy(annotation.columnsNamingStrategy());
        }
    }

    public UpdateDefinition parseFromUpdate(Update update) {
        UpdateDefinition.UpdateDefinitionBuilder<?, ?> updateDefinition = UpdateDefinition.builder();
        Table table = update.getFromItem(Table.class);
        if (Objects.nonNull(table)) {
            if (Objects.nonNull(table.getDatabase()) && StringUtils.isNotEmpty(table.getDatabase().getDatabaseName())) {
                updateDefinition.catalog(table.getDatabase().getDatabaseName());
            }
            if (StringUtils.isNotEmpty(table.getSchemaName())) {
                updateDefinition.schema(table.getSchemaName());
            }
            updateDefinition.table(table.getName());
            updateDefinition.alias(table.getAlias().toString());
        }

        StringBuilder updateSetsString = UpdateSet.appendUpdateSetsTo(new StringBuilder(), update.getUpdateSets());
        if (StringUtils.isNotEmpty(updateSetsString)) {
            updateDefinition.updateSets(updateSetsString.toString().split(","));
        }

        JdbcJoinTableParser parser = PARSER.getParser(JdbcJoinTableParser.class);

        if (Objects.nonNull(update.getJoins())) {
            updateDefinition.joinTables(update.getJoins().stream().map(parser::parseFromJoin).toArray(JoinTableDefinition[]::new));
        } else {
            updateDefinition.joinTables(new JoinTableDefinition[0]);
        }

        if (Objects.nonNull(update.getStartJoins())) {
            updateDefinition.startJoinTables(update.getStartJoins().stream().map(parser::parseFromJoin).toArray(JoinTableDefinition[]::new));
        } else {
            updateDefinition.startJoinTables(new JoinTableDefinition[0]);
        }

        if (Objects.nonNull(update.getWhere())) {
            updateDefinition.where(update.getWhere().toString());
        }

        return updateDefinition.build();
    }

}
