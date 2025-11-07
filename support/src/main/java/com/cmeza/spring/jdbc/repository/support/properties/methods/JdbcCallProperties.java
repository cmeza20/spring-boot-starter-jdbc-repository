package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;

import lombok.Data;

import java.util.Map;

@Data
public class JdbcCallProperties extends AbstractProperties {

    private String value;
    private String schema = "";
    private String catalog = "";
    private String[] parameters = new String[0];
    private JdbcCall.CallType type = JdbcCall.CallType.CALL;
    private boolean loggable;
    private Class<? extends NamingStrategy> parametersNamingStrategy = NoOpNamingStrategy.class;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("value", value);
        map.put("schema", schema);
        map.put("catalog", catalog);
        map.put("parameters", parameters);
        map.put("type", type);
        map.put("loggable", loggable);
        map.put("parametersNamingStrategy", parametersNamingStrategy);
    }
}
