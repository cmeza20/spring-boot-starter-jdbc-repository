package com.cmeza.spring.jdbc.repository.projections;

import java.util.Map;

public interface ProjectionAttributes {
    ProjectionAttributes addAttribute(String name, Object value);

    Map<String, Object> getAttributes();
}
