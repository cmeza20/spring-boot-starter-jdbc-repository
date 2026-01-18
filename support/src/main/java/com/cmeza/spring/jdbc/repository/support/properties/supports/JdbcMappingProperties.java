package com.cmeza.spring.jdbc.repository.support.properties.supports;

import com.cmeza.spring.jdbc.repository.support.binding.SqlType;
import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractSupport;
import lombok.Data;

import java.util.Map;

@Data
public class JdbcMappingProperties extends AbstractSupport {

    private String to;
    private String from = "";
    private SqlType type = new SqlType(0);

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("to", to);
        map.put("from", from);
        map.put("type", type.getValue());
    }
}
