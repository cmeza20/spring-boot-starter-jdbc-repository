package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcInsertProperties extends AbstractProperties {
    private String table;
    private String catalog = "";
    private String schema = "";
    private String[] columns = new String[0];
    private String[] generatedKeyColumns = new String[0];
    private boolean accessTableColumnMetaData = true;
    private boolean overrideIncludeSynonymsDefault;
    private boolean loggable;
    private Class<? extends NamingStrategy> columnsNamingStrategy = NoOpNamingStrategy.class;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("table", table);
        map.put("catalog", catalog);
        map.put("schema", schema);
        map.put("columns", columns);
        map.put("generatedKeyColumns", generatedKeyColumns);
        map.put("accessTableColumnMetaData", accessTableColumnMetaData);
        map.put("overrideIncludeSynonymsDefault", overrideIncludeSynonymsDefault);
        map.put("loggable", loggable);
        map.put("columnsNamingStrategy", columnsNamingStrategy);
    }
}
