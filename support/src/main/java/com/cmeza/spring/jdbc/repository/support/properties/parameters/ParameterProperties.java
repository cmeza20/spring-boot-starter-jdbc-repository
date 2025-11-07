package com.cmeza.spring.jdbc.repository.support.properties.parameters;

import com.cmeza.spring.jdbc.repository.support.binding.SqlType;
import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import lombok.Data;

import java.util.Map;

@Data
public class ParameterProperties extends AbstractProperties {
    private String value;
    private SqlType type = new SqlType(0);
    private int order;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("value", value);
        map.put("type", type.getValue());
        map.put("order", order);
    }
}
