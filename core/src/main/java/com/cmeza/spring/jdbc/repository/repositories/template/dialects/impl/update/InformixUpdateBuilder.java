package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.update;

import com.cmeza.spring.jdbc.repository.support.factories.JdbcUpdateFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultUpdateBuilder;
import org.springframework.jdbc.support.KeyHolder;

public class InformixUpdateBuilder extends DefaultUpdateBuilder {
    public InformixUpdateBuilder(String query, Impl impl) {
        super(query, impl);
    }

    public InformixUpdateBuilder(JdbcUpdateFactory jdbcUpdateFactory, Impl impl) {
        super(jdbcUpdateFactory, impl);
    }

    @Override
    public KeyHolder executeWithKey() {
        throw new UnsupportedOperationException("KeyHolder not supported for Informix Update");
    }
}
