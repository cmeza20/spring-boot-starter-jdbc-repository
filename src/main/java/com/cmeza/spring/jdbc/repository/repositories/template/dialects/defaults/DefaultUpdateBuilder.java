package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcUpdateFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

public class DefaultUpdateBuilder extends AbstractJdbcBuilder<JdbcUpdateBuilder> implements JdbcUpdateBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUpdateBuilder.class);

    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;
    private String[] keyColumnNames;
    private String query;
    private JdbcUpdateFactory jdbcUpdateFactory;

    public DefaultUpdateBuilder(String query, Impl impl) {
        super(impl);
        this.query = query;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    public DefaultUpdateBuilder(JdbcUpdateFactory jdbcUpdateFactory, Impl impl) {
        super(impl);
        this.jdbcUpdateFactory = jdbcUpdateFactory;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    @Override
    public void printExtras(Logger logger) {
        if (Objects.nonNull(jdbcUpdateFactory)) {
            jdbcUpdateFactory.print(LOGGER);
            String sql = jdbcUpdateFactory.generateUpdateQuery();
            logger.info("| Sql: [{}]", sql);
        } else if (StringUtils.isNotEmpty(query)) {
            logger.info("| Sql: [{}]", query);
        }
        logger.info("| KeyColumnNames: {}", Objects.nonNull(keyColumnNames) ? keyColumnNames : "Not specified!");
    }

    @Override
    public int execute() {
        return execute(() -> jdbcRepositoryTemplate.getJdbcOperations().update(this.jdbcRepositoryTemplate.getPreparedStatementCreator(getUpdateQuery(), getParameterSources())));
    }

    @Override
    public KeyHolder executeWithKey() {
        PreparedStatementCreator psc = this.jdbcRepositoryTemplate.getPreparedStatementCreator(getUpdateQuery(), getParameterSources(), pscf -> {
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

    private String getUpdateQuery() {
        if (Objects.nonNull(jdbcUpdateFactory)) {
            return jdbcUpdateFactory.generateUpdateQuery();
        }
        return query;
    }
}
