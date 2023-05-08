package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import org.slf4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

public class DefaultUpdateBuilder extends AbstractJdbcBuilder<JdbcUpdateBuilder> implements JdbcUpdateBuilder {

    private final String query;
    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;
    private String[] keyColumnNames;

    public DefaultUpdateBuilder(String query, Impl impl) {
        super(impl);
        this.query = query;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    @Override
    public void printExtras(Logger logger) {
        logger.info("| Sql: [{}]", query);
        logger.info("| KeyColumnNames: {}", Objects.nonNull(keyColumnNames) ? keyColumnNames : "Not specified!");
    }

    @Override
    public int execute() {
        return execute(() -> jdbcRepositoryTemplate.getJdbcOperations().update(this.jdbcRepositoryTemplate.getPreparedStatementCreator(query, getParameterSources())));
    }

    @Override
    public KeyHolder executeWithKey() {
        PreparedStatementCreator psc = this.jdbcRepositoryTemplate.getPreparedStatementCreator(query, getParameterSources(), pscf -> {
            if (keyColumnNames != null) {
                pscf.setGeneratedKeysColumnNames(keyColumnNames);
            } else {
                pscf.setReturnGeneratedKeys(true);
            }
        });
        return execute(() -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcRepositoryTemplate.getJdbcOperations().update(psc, keyHolder);
            return keyHolder;
        });
    }

    @Override
    public JdbcUpdateBuilder withKeyColumnNames(String... keyColumnNames) {
        if (Objects.nonNull(keyColumnNames) && keyColumnNames.length > 0) {
            this.keyColumnNames = keyColumnNames;
        }
        return this;
    }
}
