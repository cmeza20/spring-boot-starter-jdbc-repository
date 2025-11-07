package com.cmeza.spring.jdbc.repository.support.definitions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class QueryDefinition extends TableDefinition {
    private String[] columns;
    private String where;
    private String[] groupBy;
    private String[] orderBy;
    private String query;
    private JoinTableDefinition[] joinTables;
}
