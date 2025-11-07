package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.util.Map;

@Data
public class JdbcRawQueryProperties extends AbstractProperties {
    private String value;
    private Class<? extends RowMapper> mapper = RowMapper.class;
    private boolean loggable;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("value", value);
        map.put("mapper", mapper);
        map.put("loggable", loggable);
    }
}
