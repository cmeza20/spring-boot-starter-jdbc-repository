package com.cmeza.spring.jdbc.repository.support.properties.supports;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcJoinTableProperties extends AbstractProperties {
    private String catalog = "";
    private String schema = "";
    private String table;
    private String alias;
    private String on;
    private JdbcJoinTable.Join join = JdbcJoinTable.Join.INNER;
    private JdbcJoinTable.JoinPosition position = JdbcJoinTable.JoinPosition.NORMAL;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("catalog", catalog);
        map.put("schema", schema);
        map.put("table", table);
        map.put("alias", alias);
        map.put("on", on);
        map.put("join", join);
        map.put("position", position);
    }
}
