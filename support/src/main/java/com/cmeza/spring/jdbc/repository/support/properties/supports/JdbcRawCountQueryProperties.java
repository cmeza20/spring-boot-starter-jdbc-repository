package com.cmeza.spring.jdbc.repository.support.properties.supports;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractSupport;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcRawCountQueryProperties extends AbstractSupport {
    private String value;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("value", value);
    }
}
