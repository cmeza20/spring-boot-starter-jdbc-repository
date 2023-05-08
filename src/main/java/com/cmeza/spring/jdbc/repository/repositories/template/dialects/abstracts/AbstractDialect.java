package com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts;

public abstract class AbstractDialect {
    protected final AbstractJdbcBuilder.Impl impl;

    protected AbstractDialect(AbstractJdbcBuilder.Impl impl) {
        this.impl = impl;
    }
}
