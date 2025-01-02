package com.cmeza.spring.jdbc.repository.repositories.definitions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class UpdateDefinition extends TableDefinition {
    private String[] updateSets;
    private String where;
    private String updateQuery;
    private JoinTableDefinition[] joinTables;
    private JoinTableDefinition[] startJoinTables;
}
