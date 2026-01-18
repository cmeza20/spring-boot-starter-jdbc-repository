package com.cmeza.spring.jdbc.repository.support.definitions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class ParamFilterDefinition {
    private String[] values;
}
