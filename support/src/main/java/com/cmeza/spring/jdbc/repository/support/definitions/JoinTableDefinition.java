package com.cmeza.spring.jdbc.repository.support.definitions;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class JoinTableDefinition extends TableDefinition {
    private String on;
    private JdbcJoinTable.Join join;
    @Builder.Default
    private JdbcJoinTable.JoinPosition position = JdbcJoinTable.JoinPosition.NORMAL;
}
