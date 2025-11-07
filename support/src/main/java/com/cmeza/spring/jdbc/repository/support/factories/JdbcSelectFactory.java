package com.cmeza.spring.jdbc.repository.support.factories;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.support.definitions.QueryDefinition;
import net.sf.jsqlparser.JSQLParserException;
import org.slf4j.Logger;

public interface JdbcSelectFactory {

    JdbcSelectFactory withSchema(String schema);

    JdbcSelectFactory withCatalog(String catalog);

    JdbcSelectFactory withColumns(String... columns);

    JdbcSelectFactory withOrderBy(String... orderBy);

    JdbcSelectFactory withGroupBy(String... groupBy);

    JdbcSelectFactory withAlias(String alias);

    JdbcSelectFactory withWhere(String where);

    JdbcSelectFactory withJoinTable(JoinTableDefinition tableDefinition);

    JdbcSelectFactory withJoinTable(JoinTableDefinition[] tableDefinitions);

    JdbcSelectFactory withJoinTable(String table);

    JdbcSelectFactory withJoinTable(String table, JdbcJoinTable.Join join);

    JdbcSelectFactory withJoinTable(String table, String on, JdbcJoinTable.Join join);

    JdbcSelectFactory withJoinTable(String table, String alias, String on, JdbcJoinTable.Join join);

    JdbcSelectFactory withJoinTable(String schema, String table, String alias, String on, JdbcJoinTable.Join join);

    JdbcSelectFactory withJoinTable(String catalog, String schema, String table, String alias, String on, JdbcJoinTable.Join join);

    JdbcSelectFactory readQuery(String query) throws JSQLParserException;

    void print();

    void print(Logger log);

    String generateQuery();

    QueryDefinition generateDefinition();
}
