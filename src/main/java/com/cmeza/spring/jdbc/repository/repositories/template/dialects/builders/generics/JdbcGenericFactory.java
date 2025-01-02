package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;

public interface JdbcGenericFactory {
    JdbcSelectFactory select(String table);

    JdbcUpdateFactory update(String table, String... updateSets);
}
