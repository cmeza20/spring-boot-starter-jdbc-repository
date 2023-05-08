package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import org.springframework.jdbc.support.KeyHolder;

public interface JdbcUpdateBuilder extends JdbcBuilder<JdbcUpdateBuilder> {
    int execute();

    KeyHolder executeWithKey();

    JdbcUpdateBuilder withKeyColumnNames(String... keyColumnNames);
}
