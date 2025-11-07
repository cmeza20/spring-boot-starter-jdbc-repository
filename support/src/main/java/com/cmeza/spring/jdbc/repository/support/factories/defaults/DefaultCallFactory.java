package com.cmeza.spring.jdbc.repository.support.factories.defaults;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcCallFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Objects;

public class DefaultCallFactory implements JdbcCallFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCallFactory.class);

    private final String callName;
    private String schema;
    private String catalog;
    private String[] parameters;
    private JdbcCall.CallType type;

    public DefaultCallFactory(String callName) {
        Assert.hasLength(callName, "Call name must not be null or empty");
        this.callName = callName;
        this.type = JdbcCall.CallType.CALL;
    }

    @Override
    public JdbcCallFactory withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    @Override
    public JdbcCallFactory withCatalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    @Override
    public JdbcCallFactory withParameters(String... parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public JdbcCallFactory withType(JdbcCall.CallType type) {
        Assert.notNull(type, "Call type must not be null");
        this.type = type;
        return this;
    }

    @Override
    public void print() {
        print(LOGGER);
    }

    @Override
    public void print(Logger log) {
        if (StringUtils.isNotEmpty(catalog)) {
            log.info("| Catalog: {}", catalog);
        }
        if (StringUtils.isNotEmpty(schema)) {
            log.info("| Schema: {}", schema);
        }
        log.info("| Name: {}", callName);
        if (Objects.nonNull(parameters) && parameters.length > 0) {
            String params = StringUtils.join(parameters, ", ");
            log.info("| Parameters: {}", params);
        }
        String sql = generateCallQuery();
        log.info("| Sql: {}", sql);
    }

    @Override
    public String generateCallQuery() {
        StringBuilder builder = new StringBuilder(type.name() + " ");
        if (StringUtils.isNotEmpty(catalog)) {
            builder.append(catalog).append(".");
        }
        if (StringUtils.isNotEmpty(schema)) {
            builder.append(schema).append(".");
        }
        builder.append(callName);
        if (type.isParenthesis()) {
            builder.append("(");
        }
        builder.append(StringUtils.join(parameters, ", "));
        if (type.isParenthesis()) {
            builder.append(")");
        }
        return builder.toString();
    }
}
