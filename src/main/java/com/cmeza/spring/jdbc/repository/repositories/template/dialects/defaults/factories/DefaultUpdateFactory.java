package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.factories;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.definitions.TableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.definitions.UpdateDefinition;
import com.cmeza.spring.jdbc.repository.repositories.mappers.FromTableDefinitionMapper;
import com.cmeza.spring.jdbc.repository.repositories.mappers.JoinTableDefinitionMapper;
import com.cmeza.spring.jdbc.repository.repositories.mappers.UpdateDefinitionMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;

public class DefaultUpdateFactory implements JdbcUpdateFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUpdateFactory.class);
    private final Map<Integer, String> sqlQueryCache = new LinkedHashMap<>();
    private final UpdateDefinitionMapper updateDefinitionMapper = new UpdateDefinitionMapper();
    private final JoinTableDefinitionMapper joinTableDefinitionMapper = new JoinTableDefinitionMapper();
    private final FromTableDefinitionMapper fromTableDefinitionMapper = new FromTableDefinitionMapper();
    private final StringBuilder queryHashcode = new StringBuilder();
    private Update update;

    public DefaultUpdateFactory(String table, String... updateSets) {
        Assert.hasLength(table, "Table must not be empty");
        Assert.notEmpty(updateSets, "UpdateSets must not be empty");

        queryHashcode.append(table);

        List<UpdateSet> updateSetList = new ArrayList<>();
        for (String updateSet : updateSets) {
            Assert.hasLength(updateSet, "UpdateSet must not be empty");
            Assert.isTrue(updateSet.contains("="), "UpdateSet needs a fairness operator");

            String[] parts = updateSet.split("=");
            updateSetList.add(new UpdateSet(new Column(parts[0]), new Column(parts[1])));

            queryHashcode.append(updateSet);
        }

        this.update = new Update();
        this.update.setTable(new Table().withName(table));
        this.update.setUpdateSets(updateSetList);
    }

    @Override
    public JdbcUpdateFactory withSchema(String schema) {
        if (StringUtils.isNotEmpty(schema)) {
            this.queryHashcode.append(schema);
            this.update.getTable().withSchemaName(schema);
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory withCatalog(String catalog) {
        if (StringUtils.isNotEmpty(catalog)) {
            this.queryHashcode.append(catalog);
            this.update.getTable().withDatabase(new Database(catalog));
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory withAlias(String alias) {
        if (StringUtils.isNotEmpty(alias)) {
            this.queryHashcode.append(alias);
            this.update.getTable().withAlias(new Alias(alias, false));
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory withWhere(String where) {
        if (StringUtils.isNotEmpty(where)) {
            this.queryHashcode.append(where);
            this.update.setWhere(new Column(where));
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory withFromTable(TableDefinition tableDefinition) {
        if (Objects.nonNull(tableDefinition)) {
            this.queryHashcode.append(tableDefinition.getCatalog())
                    .append(tableDefinition.getSchema())
                    .append(tableDefinition.getTable())
                    .append(tableDefinition.getAlias());
            this.update.setFromItem(fromTableDefinitionMapper.parseToTable(tableDefinition));
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory withFromTable(String table) {
        this.withFromTable(null, null, table, null);
        return this;
    }

    @Override
    public JdbcUpdateFactory withFromTable(String catalog, String schema, String table) {
        this.withFromTable(catalog, schema, table, null);
        return this;
    }

    @Override
    public JdbcUpdateFactory withFromTable(String catalog, String schema, String table, String alias) {
        this.assertTableName(table);

        TableDefinition tableDefinition = TableDefinition.builder()
                .table(table)
                .catalog(catalog)
                .schema(schema)
                .alias(alias)
                .build();

        this.queryHashcode.append(table);
        this.update.setFromItem(fromTableDefinitionMapper.parseToTable(tableDefinition));

        return this;
    }


    @Override
    public JdbcUpdateFactory withJoinTable(JoinTableDefinition tableDefinition) {
        if (Objects.nonNull(tableDefinition)) {
            assertTableJoin(tableDefinition.getJoin());
            assertTableJoinPosition(tableDefinition.getPosition());
            this.queryHashcode.append(tableDefinition.getCatalog())
                    .append(tableDefinition.getSchema())
                    .append(tableDefinition.getTable())
                    .append(tableDefinition.getAlias())
                    .append(tableDefinition.getOn())
                    .append(tableDefinition.getJoin().name())
                    .append(tableDefinition.getPosition().name());

            if (tableDefinition.getPosition().equals(JdbcJoinTable.JoinPosition.NORMAL)) {
                this.update.addJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
            } else {
                this.update.addStartJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
            }
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory withJoinTable(JoinTableDefinition[] tableDefinitions) {
        if (Objects.nonNull(tableDefinitions)) {
            Arrays.stream(tableDefinitions).forEach(t -> {
                assertTableJoin(t.getJoin());
                assertTableJoinPosition(t.getPosition());

                this.queryHashcode.append(t.getCatalog())
                        .append(t.getSchema())
                        .append(t.getTable())
                        .append(t.getAlias())
                        .append(t.getOn())
                        .append(t.getJoin().name())
                        .append(t.getPosition().name());
                if (t.getPosition().equals(JdbcJoinTable.JoinPosition.NORMAL)) {
                    this.update.addJoins(joinTableDefinitionMapper.parseToJoin(t));
                } else {
                    this.update.addStartJoins(joinTableDefinitionMapper.parseToJoin(t));
                }
            });
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory withJoinTable(String table) {
        this.assertTableName(table);

        JoinTableDefinition tableDefinition = JoinTableDefinition.builder()
                .table(table)
                .join(JdbcJoinTable.Join.INNER)
                .build();

        this.queryHashcode.append(table);
        this.update.addJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
        return this;
    }

    @Override
    public JdbcUpdateFactory withJoinTable(String table, JdbcJoinTable.Join join) {
        this.assertTableName(table);
        this.assertTableJoin(join);

        JoinTableDefinition tableDefinition = JoinTableDefinition.builder()
                .table(table)
                .join(JdbcJoinTable.Join.INNER)
                .build();

        this.queryHashcode.append(table).append(join.name());
        this.update.addJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
        return this;
    }

    @Override
    public JdbcUpdateFactory withJoinTable(String table, String on, JdbcJoinTable.Join join) {
        this.assertTableName(table);
        this.assertTableJoin(join);
        Assert.hasLength(on, "Join on must not be empty");

        JoinTableDefinition tableDefinition = JoinTableDefinition.builder()
                .table(table)
                .join(join)
                .on(on)
                .build();
        this.queryHashcode.append(table).append(on).append(join.name());
        this.update.addJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
        return this;
    }

    @Override
    public JdbcUpdateFactory withJoinTable(String table, String alias, String on, JdbcJoinTable.Join join) {
        this.assertTableName(table);
        this.assertTableJoin(join);
        Assert.hasLength(alias, "Table alias must not be empty");
        Assert.hasLength(on, "Join on must not be empty");

        JoinTableDefinition tableDefinition = JoinTableDefinition.builder()
                .table(table)
                .alias(alias)
                .join(join)
                .on(on)
                .build();

        this.queryHashcode.append(table).append(alias).append(on).append(join.name());
        this.update.addJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
        return this;
    }

    @Override
    public JdbcUpdateFactory withJoinTable(String schema, String table, String alias, String on, JdbcJoinTable.Join join) {
        return withJoinTable(null, schema, table, alias, on, join);
    }

    @Override
    public JdbcUpdateFactory withJoinTable(String catalog, String schema, String table, String alias, String on, JdbcJoinTable.Join join) {
        this.withJoinTable(catalog, schema, table, alias, on, join, JdbcJoinTable.JoinPosition.NORMAL);
        return this;
    }

    @Override
    public JdbcUpdateFactory withJoinTable(String catalog, String schema, String table, String alias, String on, JdbcJoinTable.Join join, JdbcJoinTable.JoinPosition position) {
        this.assertTableName(table);
        this.assertTableJoin(join);
        this.assertTableJoinPosition(position);
        Assert.hasLength(alias, "Table alias must not be empty");

        JoinTableDefinition tableDefinition = JoinTableDefinition.builder()
                .catalog(catalog)
                .schema(schema)
                .table(table)
                .alias(alias)
                .join(join)
                .on(on)
                .position(position)
                .build();

        this.queryHashcode.append(catalog).append(schema).append(table).append(alias).append(on).append(join.name());
        if (position.equals(JdbcJoinTable.JoinPosition.NORMAL)) {
            this.update.addJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
        } else {
            this.update.addStartJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
        }
        return this;
    }

    @Override
    public JdbcUpdateFactory readQuery(String query) throws JSQLParserException {
        queryHashcode.setLength(0);
        queryHashcode.append(query);
        this.update = (Update) CCJSqlParserUtil.parse(query);
        return this;
    }

    @Override
    public void print() {
        print(LOGGER);
    }

    @Override
    public void print(Logger logger) {
        Table table = update.getTable();
        if (Objects.nonNull(table.getDatabase()) && StringUtils.isNotEmpty(table.getDatabase().getDatabaseName())) {
            logger.info("| Catalog: {}", table.getDatabase().getDatabaseName());
        }
        if (StringUtils.isNotEmpty(table.getSchemaName())) {
            logger.info("| Schema: {}", table.getSchemaName());
        }
        logger.info("| Table: {}", table);

        String updateSets = UpdateSet.appendUpdateSetsTo(new StringBuilder(), update.getUpdateSets()).toString();
        logger.info("| Update Sets: [{}]", updateSets);

        if (Objects.nonNull(update.getFromItem())) {
            Table tab = (Table) update.getFromItem();
            logger.info("| From: {}", tab);
        }

        if (Objects.nonNull(update.getJoins())) {
            update.getJoins().forEach(jo -> logger.info("| Normal Join: [{}]", jo));
        }

        if (Objects.nonNull(update.getStartJoins())) {
            update.getStartJoins().forEach(jo -> logger.info("| Start Join: [{}]", jo));
        }

        if (Objects.nonNull(update.getWhere())) {
            logger.info("| Where: [{}]", update.getWhere());
        }

    }

    @Override
    public String generateUpdateQuery() {
        int hashcode = this.queryHashcode.toString().hashCode();
        return getSqlQuery(this.update, hashcode);
    }


    @Override
    public UpdateDefinition generateDefinition() {
        UpdateDefinition updateDefinition = updateDefinitionMapper.parseFromUpdate(this.update);
        updateDefinition.setUpdateQuery(generateUpdateQuery());
        return updateDefinition;
    }

    private String getSqlQuery(Update update, int hashcode) {
        String updateQuery;
        if ((updateQuery = sqlQueryCache.get(hashcode)) != null) {
            return updateQuery;
        }

        synchronized (sqlQueryCache) {
            updateQuery = update.toString();
            sqlQueryCache.put(hashcode, updateQuery);
            return updateQuery;
        }
    }

    private void assertTableName(String table) {
        Assert.hasLength(table, "Table name must not be empty");
    }

    private void assertTableJoin(JdbcJoinTable.Join join) {
        Assert.notNull(join, "Table Join required");
    }

    private void assertTableJoinPosition(JdbcJoinTable.JoinPosition position) {
        Assert.notNull(position, "Join position required");
    }
}
