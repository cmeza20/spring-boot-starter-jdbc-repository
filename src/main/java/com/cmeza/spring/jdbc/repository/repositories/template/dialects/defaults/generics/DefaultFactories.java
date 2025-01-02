package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.generics;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.factories.DefaultSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.factories.DefaultUpdateFactory;

public class DefaultFactories implements JdbcGenericFactory {
    @Override
    public JdbcSelectFactory select(String table) {
        return new DefaultSelectFactory(table);
    }

    @Override
    public JdbcUpdateFactory update(String table, String... updateSets) {
        return new DefaultUpdateFactory(table, updateSets);
    }
}
