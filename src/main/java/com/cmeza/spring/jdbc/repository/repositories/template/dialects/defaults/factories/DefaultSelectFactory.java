package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.factories;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.repositories.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.repositories.definitions.QueryDefinition;
import com.cmeza.spring.jdbc.repository.repositories.mappers.JoinTableDefinitionMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.mappers.QueryDefinitionMapper;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Database;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.util.SelectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;

public class DefaultSelectFactory implements JdbcSelectFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSelectFactory.class);
    private static final String ALIAS_DEFAULT = "t";
    private final Map<Integer, String> sqlQueryCache = new LinkedHashMap<>();
    private final QueryDefinitionMapper queryDefinitionMapper = new QueryDefinitionMapper();
    private final JoinTableDefinitionMapper joinTableDefinitionMapper = new JoinTableDefinitionMapper();
    private PlainSelect select;
    StringBuilder queryHashcode = new StringBuilder();
    private boolean defaultColumns = true;

    public DefaultSelectFactory(String table) {
        Assert.hasLength(table, "Table must not be empty");
        queryHashcode.append(table);
        queryHashcode.append(ALIAS_DEFAULT);
        this.select = (PlainSelect) SelectUtils.buildSelectFromTable(new Table(table).withAlias(new Alias(ALIAS_DEFAULT, false)));
    }

    @Override
    public JdbcSelectFactory withSchema(String schema) {
        if (StringUtils.isNotEmpty(schema)) {
            this.queryHashcode.append(schema);
            ((Table) this.select.getFromItem()).withSchemaName(schema);
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withCatalog(String catalog) {
        if (StringUtils.isNotEmpty(catalog)) {
            this.queryHashcode.append(catalog);
            ((Table) this.select.getFromItem()).withDatabase(new Database(catalog));
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withColumns(String... columns) {
        if (Objects.nonNull(columns)) {
            if (defaultColumns) {
                this.defaultColumns = false;
                this.select.getSelectItems().clear();
            }
            Arrays.stream(columns).forEach(c -> {
                this.queryHashcode.append(c);
                select.addSelectItem(new Column(c));
            });
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withOrderBy(String... orderBy) {
        if (Objects.nonNull(orderBy)) {
            Arrays.stream(orderBy).forEach(c -> {
                this.queryHashcode.append(c);
                select.addOrderByElements(new OrderByElement().withExpression(new Column(c)));
            });
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withGroupBy(String... groupBy) {
        if (Objects.nonNull(groupBy)) {
            Arrays.stream(groupBy).forEach(c -> {
                this.queryHashcode.append(c);
                select.addGroupByColumnReference(new Column(c));
            });
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withAlias(String alias) {
        if (StringUtils.isNotEmpty(alias)) {
            this.queryHashcode.append(alias);
            this.select.getFromItem().withAlias(new Alias(alias, false));
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withWhere(String where) {
        if (StringUtils.isNotEmpty(where)) {
            this.queryHashcode.append(where);
            this.select.setWhere(new Column(where));
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withJoinTable(JoinTableDefinition tableDefinition) {
        if (Objects.nonNull(tableDefinition)) {
            this.queryHashcode.append(tableDefinition.getCatalog())
                    .append(tableDefinition.getSchema())
                    .append(tableDefinition.getTable())
                    .append(tableDefinition.getAlias())
                    .append(tableDefinition.getOn())
                    .append(tableDefinition.getJoin().name())
                    .append(tableDefinition.getPosition().name());
            this.select.addJoins(joinTableDefinitionMapper.parseToJoin(tableDefinition));
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withJoinTable(JoinTableDefinition[] tableDefinitions) {
        if (Objects.nonNull(tableDefinitions)) {
            Arrays.stream(tableDefinitions).forEach(t -> {
                this.queryHashcode.append(t.getCatalog())
                        .append(t.getSchema())
                        .append(t.getTable())
                        .append(t.getAlias())
                        .append(t.getOn())
                        .append(t.getJoin().name())
                        .append(t.getPosition().name());
                this.select.addJoins(joinTableDefinitionMapper.parseToJoin(t));
            });
        }
        return this;
    }

    @Override
    public JdbcSelectFactory withJoinTable(String table) {
        this.assertTableName(table);
        this.queryHashcode.append(table);

        Table tableObject = new Table(table);
        tableObject.setAlias(new Alias(ALIAS_DEFAULT, false));
        Join join = new Join().setFromItem(tableObject);
        join.setInner(true);

        this.select.addJoins(join);
        return this;
    }

    @Override
    public JdbcSelectFactory withJoinTable(String table, JdbcJoinTable.Join join) {
        this.assertTableName(table);
        this.assertTableJoin(join);

        this.queryHashcode.append(table).append(join.name());

        this.select.addJoins(joinTableDefinitionMapper.parseToJoin(JoinTableDefinition.builder()
                .table(table)
                .alias(ALIAS_DEFAULT)
                .join(JdbcJoinTable.Join.INNER)
                .build()));
        return this;
    }

    @Override
    public JdbcSelectFactory withJoinTable(String table, String on, JdbcJoinTable.Join join) {
        this.assertTableName(table);
        this.assertTableJoin(join);
        Assert.hasLength(on, "Join on must not be empty");

        this.queryHashcode.append(table).append(on).append(join.name());

        this.select.addJoins(joinTableDefinitionMapper.parseToJoin(JoinTableDefinition.builder()
                .table(table)
                .alias(ALIAS_DEFAULT)
                .join(join)
                .on(on)
                .build()));
        return this;
    }

    @Override
    public JdbcSelectFactory withJoinTable(String table, String alias, String on, JdbcJoinTable.Join join) {
        this.assertTableName(table);
        this.assertTableJoin(join);
        Assert.hasLength(alias, "Table alias must not be empty");
        Assert.hasLength(on, "Join on must not be empty");

        this.queryHashcode.append(table).append(alias).append(on).append(join.name());

        this.select.addJoins(joinTableDefinitionMapper.parseToJoin(JoinTableDefinition.builder()
                .table(table)
                .alias(alias)
                .join(join)
                .on(on)
                .build()));

        return this;
    }

    @Override
    public JdbcSelectFactory withJoinTable(String schema, String table, String alias, String on, JdbcJoinTable.Join join) {
        return withJoinTable(null, schema, table, alias, on, join);
    }

    @Override
    public JdbcSelectFactory withJoinTable(String catalog, String schema, String table, String alias, String on, JdbcJoinTable.Join join) {
        this.assertTableName(table);
        this.assertTableJoin(join);
        Assert.hasLength(alias, "Table alias must not be empty");

        this.queryHashcode.append(catalog).append(schema).append(table).append(alias).append(on).append(join.name());

        this.select.addJoins(joinTableDefinitionMapper.parseToJoin(JoinTableDefinition.builder()
                .catalog(catalog)
                .schema(schema)
                .table(table)
                .alias(alias)
                .join(join)
                .on(on)
                .build()));

        return this;
    }

    @Override
    public JdbcSelectFactory readQuery(String query) throws JSQLParserException {
        queryHashcode.setLength(0);
        queryHashcode.append(query);
        this.select = (PlainSelect) CCJSqlParserUtil.parse(query);
        return this;
    }

    @Override
    public void print() {
        print(LOGGER);
    }

    @Override
    public void print(Logger logger) {
        Table table = select.getFromItem(Table.class);
        if (Objects.nonNull(table.getDatabase()) && StringUtils.isNotEmpty(table.getDatabase().getDatabaseName())) {
            logger.info("| Catalog: {}", table.getDatabase().getDatabaseName());
        }
        if (StringUtils.isNotEmpty(table.getSchemaName())) {
            logger.info("| Schema: {}", table.getSchemaName());
        }
        logger.info("| Table: {}{}", table.getName(), table.getAlias());

        String columns = StringUtils.join(select.getSelectItems(), ", ");
        logger.info("| Columns: [{}]", columns);

        if (Objects.nonNull(select.getJoins())) {
            select.getJoins().forEach(jo -> logger.info("| Join: [{}]", jo));
        }

        if (Objects.nonNull(select.getWhere())) {
            logger.info("| Where: [{}]", select.getWhere());
        }

        if (Objects.nonNull(select.getGroupBy())) {
            String groupBy = select.getGroupBy().getGroupByExpressionList().toString();
            logger.info("| Group By: {}", groupBy);
        }

        if (Objects.nonNull(select.getOrderByElements())) {
            String orderBy = StringUtils.join(select.getOrderByElements(), ", ");
            logger.info("| Order By: {}", orderBy);
        }
    }

    @Override
    public String generateQuery() {
        int hashcode = this.queryHashcode.toString().hashCode();
        return getSqlQuery(this.select, hashcode);
    }

    @Override
    public QueryDefinition generateDefinition() {
        QueryDefinition queryDefinition = queryDefinitionMapper.parseFromSelect(this.select);
        queryDefinition.setQuery(generateQuery());
        return queryDefinition;
    }

    private String getSqlQuery(Select select, int hashcode) {
        String query;
        if ((query = sqlQueryCache.get(hashcode)) != null) {
            return query;
        }

        synchronized (sqlQueryCache) {
            query = select.toString();
            sqlQueryCache.put(hashcode, query);
            return query;
        }
    }

    private void assertTableName(String table) {
        Assert.hasLength(table, "Table name must not be empty");
    }

    private void assertTableJoin(JdbcJoinTable.Join join) {
        Assert.notNull(join, "Table Join required");
    }
}
