package com.cmeza.spring.jdbc.repository.repositories.mappers;

import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.definitions.UpdateDefinition;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class UpdateDefinitionMapper {

    private final JoinTableDefinitionMapper joinTableDefinitionMapper = new JoinTableDefinitionMapper();

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

        if (Objects.nonNull(update.getJoins())) {
            updateDefinition.joinTables(update.getJoins().stream().map(joinTableDefinitionMapper::parseFromJoin).toArray(JoinTableDefinition[]::new));
        } else {
            updateDefinition.joinTables(new JoinTableDefinition[0]);
        }

        if (Objects.nonNull(update.getStartJoins())) {
            updateDefinition.startJoinTables(update.getStartJoins().stream().map(joinTableDefinitionMapper::parseFromJoin).toArray(JoinTableDefinition[]::new));
        } else {
            updateDefinition.startJoinTables(new JoinTableDefinition[0]);
        }

        if (Objects.nonNull(update.getWhere())) {
            updateDefinition.where(update.getWhere().toString());
        }

        return updateDefinition.build();
    }

}
