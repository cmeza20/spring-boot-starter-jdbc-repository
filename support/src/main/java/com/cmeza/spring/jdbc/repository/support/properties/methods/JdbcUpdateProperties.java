package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcFromTableProperties;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcJoinTableProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JdbcUpdateProperties extends AbstractProperties {
    private String schema = "";
    private String catalog = "";
    private String[] updateSets;
    private String table;
    private String alias = "";
    private String where = "";
    private String[] keyColumnNames = new String[0];
    private boolean loggable;
    private Class<? extends NamingStrategy> columnsNamingStrategy = NoOpNamingStrategy.class;

    private JdbcFromTableProperties fromTable;
    private List<JdbcJoinTableProperties> joinTables;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("schema", schema);
        map.put("catalog", catalog);
        map.put("updateSets", updateSets);
        map.put("table", table);
        map.put("alias", alias);
        map.put("where", where);
        map.put("keyColumnNames", keyColumnNames);
        map.put("loggable", loggable);
        map.put("columnsNamingStrategy", columnsNamingStrategy);
    }
}
