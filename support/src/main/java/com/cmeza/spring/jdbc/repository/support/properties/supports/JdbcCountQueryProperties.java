package com.cmeza.spring.jdbc.repository.support.properties.supports;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcCountQueryProperties extends AbstractProperties {

    private String schema = "";
    private String catalog = "";
    private String[] columns = new String[0];
    private String[] groupBy = new String[0];
    private String table;
    private String alias = "t";
    private String where = "";
    private Class<? extends NamingStrategy> columnsNamingStrategy = NoOpNamingStrategy.class;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("schema", schema);
        map.put("catalog", catalog);
        map.put("columns", columns);
        map.put("groupBy", groupBy);
        map.put("table", table);
        map.put("alias", alias);
        map.put("where", where);
        map.put("columnsNamingStrategy", columnsNamingStrategy);
    }

}
