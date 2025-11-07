package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics;

import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcUpdateFactory;

public interface JdbcGenericFactory {
    JdbcSelectFactory select(String table);

    JdbcUpdateFactory update(String table, String... updateSets);
}
