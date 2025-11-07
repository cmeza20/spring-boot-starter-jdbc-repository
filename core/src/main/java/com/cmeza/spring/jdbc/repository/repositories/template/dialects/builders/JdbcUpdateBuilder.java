package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;
import org.springframework.jdbc.support.KeyHolder;

public interface JdbcUpdateBuilder extends JdbcGenericBuilder<JdbcUpdateBuilder> {
    int execute();

    KeyHolder executeWithKey();

    JdbcUpdateBuilder withKeyColumnNames(String... keyColumnNames);
}
