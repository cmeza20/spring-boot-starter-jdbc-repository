package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.support.definitions.QueryDefinition;
import com.cmeza.spring.jdbc.repository.support.exceptions.JdbcPaginationException;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DefaultPaginationBuilder extends AbstractPaginationBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJdbcBuilder.class);
    private JdbcSelectFactory selectBuilder;
    private QueryDefinition countQueryDefinition;
    private String query;
    private String customCountQuery;
    private JdbcPageRequest pageRequest;

    public DefaultPaginationBuilder(String query, Impl impl) {
        super(impl);
        this.query = query;
    }

    public DefaultPaginationBuilder(JdbcSelectFactory selectBuilder, Impl impl) {
        super(impl);
        this.selectBuilder = selectBuilder;
    }

    @Override
    public void printExtras(Logger logger) {
        if (Objects.nonNull(selectBuilder)) {
            selectBuilder.print(LOGGER);
        }
    }

    @Override
    public JdbcPaginationBuilder withPageRequest(JdbcPageRequest pageRequest) {
        if (Objects.isNull(pageRequest)) {
            pageRequest = JdbcPageRequest.ofPage(1, 10);
        }
        this.pageRequest = pageRequest;
        return this;
    }

    @Override
    public JdbcPaginationBuilder withCountQuery(String customCountquery) {
        this.customCountQuery = customCountquery;
        return this;
    }

    @Override
    public JdbcPaginationBuilder withCountQuery(QueryDefinition queryDefinition) {
        this.countQueryDefinition = queryDefinition;
        return this;
    }

    @Override
    public <R> JdbcPage<R> fetchPage() {
        return fetchPage(pageRequest);
    }

    @Override
    public <R> JdbcPage<R> fetchPage(Class<R> resultType) {
        this.resultTypeRequired(resultType);
        this.withPageRequest(null);
        this.createRowMapperIfnotExists(resultType);
        return fetchPage(getQuery(), getCountQuery(), this.pageRequest, getRowMapper());
    }

    @Override
    public <R> JdbcPage<R> fetchPage(JdbcPageRequest pageRequest) {
        this.rowMapperRequired();
        this.withPageRequest(pageRequest);
        return fetchPage(getQuery(), getCountQuery(), this.pageRequest, getRowMapper());
    }

    @Override
    public <R> JdbcPage<R> fetchPage(JdbcPageRequest pageRequest, Class<R> resultType) {
        this.resultTypeRequired(resultType);
        this.withPageRequest(pageRequest);
        this.createRowMapperIfnotExists(resultType);
        return fetchPage(getQuery(), getCountQuery(), this.pageRequest, getRowMapper());
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        throw new JdbcPaginationException("You have to implement the dialect for pagination");
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        throw new JdbcPaginationException("You have to implement the dialect for pagination");
    }

    private String getQuery() {
        if (Objects.nonNull(selectBuilder)) {
            return selectBuilder.generateQuery();
        }
        return query;
    }

    private String getCountQuery() {
        if (Objects.nonNull(countQueryDefinition)) {
            return countQueryDefinition.getQuery();
        }
        return customCountQuery;
    }
}
