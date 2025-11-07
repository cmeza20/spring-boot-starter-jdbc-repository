package com.cmeza.spring.jdbc.repository.support.definitions;

import lombok.Data;

@Data
public class MappingDefinition {
    private String to;
    private String from;
    private int type;
    private int position;
    private Object value;
}
