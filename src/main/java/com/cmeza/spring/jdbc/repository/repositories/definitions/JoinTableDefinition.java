package com.cmeza.spring.jdbc.repository.repositories.definitions;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcJoinTable;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

@Data
@SuperBuilder(toBuilder = true)
public class JoinTableDefinition extends TableDefinition {
    private String on;
    private JdbcJoinTable.Join join;
    @Builder.Default
    private JdbcJoinTable.JoinPosition position = JdbcJoinTable.JoinPosition.NORMAL;
}
