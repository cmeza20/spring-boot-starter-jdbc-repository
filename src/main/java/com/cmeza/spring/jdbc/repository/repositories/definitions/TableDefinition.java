package com.cmeza.spring.jdbc.repository.repositories.definitions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class TableDefinition {
    private String catalog;
    private String schema;
    private String table;
    private String alias;
}
