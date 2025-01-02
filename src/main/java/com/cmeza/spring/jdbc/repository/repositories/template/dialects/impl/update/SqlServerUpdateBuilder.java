package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.update;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultUpdateBuilder;
import org.springframework.jdbc.support.KeyHolder;

public class SqlServerUpdateBuilder extends DefaultUpdateBuilder {
    public SqlServerUpdateBuilder(String query, Impl impl) {
        super(query, impl);
    }

    public SqlServerUpdateBuilder(JdbcUpdateFactory jdbcUpdateFactory, Impl impl) {
        super(jdbcUpdateFactory, impl);
    }

    @Override
    public KeyHolder executeWithKey() {
        throw new UnsupportedOperationException("KeyHolder not supported for SqlServer Update");
    }
}
