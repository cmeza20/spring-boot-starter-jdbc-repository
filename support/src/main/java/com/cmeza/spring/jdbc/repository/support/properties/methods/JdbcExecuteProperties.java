package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcExecuteProperties extends AbstractProperties {

    private String value;
    private boolean loggable;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("value", value);
        map.put("loggable", loggable);
    }
}
