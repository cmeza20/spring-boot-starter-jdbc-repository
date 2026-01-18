package com.cmeza.spring.jdbc.repository.support.properties.supports;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractSupport;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcFromTableProperties extends AbstractSupport {

    private String catalog = "";
    private String schema = "";
    private String table;
    private String alias;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("catalog", catalog);
        map.put("schema", schema);
        map.put("table", table);
        map.put("alias", alias);
    }
}
