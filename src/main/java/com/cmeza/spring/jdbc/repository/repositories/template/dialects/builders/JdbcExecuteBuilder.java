package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;

public interface JdbcExecuteBuilder extends JdbcGenericBuilder<JdbcExecuteBuilder> {
    int execute();

    int[] executeBatch();
}
