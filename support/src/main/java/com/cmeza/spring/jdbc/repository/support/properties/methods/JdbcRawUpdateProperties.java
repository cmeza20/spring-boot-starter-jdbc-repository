package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcRawUpdateProperties extends AbstractProperties {

    private String value;
    private String[] keyColumnNames = new String[]{};
    private boolean loggable;
    private Class<? extends NamingStrategy> columnsNamingStrategy = NoOpNamingStrategy.class;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("value", value);
        map.put("keyColumnNames", keyColumnNames);
        map.put("loggable", loggable);
        map.put("columnsNamingStrategy", columnsNamingStrategy);
    }
}
