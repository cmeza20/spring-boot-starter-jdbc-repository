package com.cmeza.spring.jdbc.repository.support.properties.abstracts;

import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcMappingProperties;
import com.cmeza.spring.jdbc.repository.support.transform.MapTransform;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractProperties implements MapTransform {
    private List<JdbcMappingProperties> mappings;

    public abstract void mapProperties(Map<String, Object> map);

    @Override
    public Map<String, Object> transform() {
        Map<String, Object> map = new HashMap<>();
        this.mapProperties(map);
        return map;
    }
}
