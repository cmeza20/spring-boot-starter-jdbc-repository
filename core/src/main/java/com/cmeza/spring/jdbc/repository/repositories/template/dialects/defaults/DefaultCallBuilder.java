package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcCallBuilder;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcCallFactory;
import com.cmeza.spring.jdbc.repository.support.factories.defaults.DefaultCallFactory;
import org.slf4j.Logger;

public class DefaultCallBuilder extends AbstractJdbcBuilder<JdbcCallBuilder> implements JdbcCallBuilder {

    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;
    private final JdbcCallFactory jdbcCallFactory;

    public DefaultCallBuilder(String callName, Impl impl) {
        super(impl);
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
        this.jdbcCallFactory = new DefaultCallFactory(callName);
    }

    @Override
    public void printExtras(Logger logger) {
        jdbcCallFactory.print(logger);
    }

    @Override
    public void execute() {
        execute(() -> {
            jdbcRepositoryTemplate.getJdbcOperations().update(this.jdbcRepositoryTemplate.getPreparedStatementCreator(jdbcCallFactory.generateCallQuery(), getParameterSources()));
            return Void.TYPE;
        });
    }

    @Override
    public JdbcCallBuilder withSchema(String schema) {
        jdbcCallFactory.withSchema(schema);
        return this;
    }

    @Override
    public JdbcCallBuilder withCatalog(String catalog) {
        jdbcCallFactory.withCatalog(catalog);
        return this;
    }

    @Override
    public JdbcCallBuilder withParameters(String... parameters) {
        jdbcCallFactory.withParameters(parameters);
        return this;
    }

    @Override
    public JdbcCallBuilder withType(JdbcCall.CallType type) {
        jdbcCallFactory.withType(type);
        return this;
    }
}
