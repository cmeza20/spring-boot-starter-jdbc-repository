package com.cmeza.spring.jdbc.repository.support.factories;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.support.definitions.JoinTableDefinition;
import com.cmeza.spring.jdbc.repository.support.definitions.TableDefinition;
import com.cmeza.spring.jdbc.repository.support.definitions.UpdateDefinition;
import net.sf.jsqlparser.JSQLParserException;
import org.slf4j.Logger;

public interface JdbcUpdateFactory {

    JdbcUpdateFactory withSchema(String schema);

    JdbcUpdateFactory withCatalog(String catalog);

    JdbcUpdateFactory withAlias(String alias);

    JdbcUpdateFactory withWhere(String where);

    JdbcUpdateFactory withFromTable(TableDefinition tableDefinition);

    JdbcUpdateFactory withFromTable(String table);

    JdbcUpdateFactory withFromTable(String catalog, String schema, String table);

    JdbcUpdateFactory withFromTable(String catalog, String schema, String table, String alias);

    JdbcUpdateFactory withJoinTable(JoinTableDefinition tableDefinition);

    JdbcUpdateFactory withJoinTable(JoinTableDefinition[] tableDefinitions);

    JdbcUpdateFactory withJoinTable(String table);

    JdbcUpdateFactory withJoinTable(String table, JdbcJoinTable.Join join);

    JdbcUpdateFactory withJoinTable(String table, String on, JdbcJoinTable.Join join);

    JdbcUpdateFactory withJoinTable(String table, String alias, String on, JdbcJoinTable.Join join);

    JdbcUpdateFactory withJoinTable(String schema, String table, String alias, String on, JdbcJoinTable.Join join);

    JdbcUpdateFactory withJoinTable(String catalog, String schema, String table, String alias, String on, JdbcJoinTable.Join join);

    JdbcUpdateFactory withJoinTable(String catalog, String schema, String table, String alias, String on, JdbcJoinTable.Join join, JdbcJoinTable.JoinPosition position);

    JdbcUpdateFactory readQuery(String query) throws JSQLParserException;

    void print();

    void print(Logger log);

    String generateUpdateQuery();

    UpdateDefinition generateDefinition();
}
