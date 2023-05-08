package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.exceptions.JdbcPaginationException;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;
import org.slf4j.Logger;
import org.springframework.util.Assert;

public class DefaultPaginationBuilder extends AbstractPaginationBuilder {
    private final String query;
    private String customCountquery;
    private JdbcPageRequest pageRequest;

    public DefaultPaginationBuilder(String query, Impl impl) {
        super(impl);
        this.query = query;
    }

    @Override
    public void printExtras(Logger logger) {
        //Override print extras
    }

    @Override
    public JdbcPaginationBuilder withPageRequest(JdbcPageRequest pageRequest) {
        this.pageRequest = pageRequest;
        return this;
    }

    @Override
    public JdbcPaginationBuilder withCountQuery(String customCountquery) {
        this.customCountquery = customCountquery;
        return this;
    }

    @Override
    public <R> JdbcPage<R> fetchPage() {
        return fetchPage(pageRequest);
    }

    @Override
    public <R> JdbcPage<R> fetchPage(Class<R> resultType) {
        this.resultTypeRequired(resultType);
        this.pageRequestRequired();
        this.createRowMapperIfnotExists(resultType);
        return execute(() -> fetchPage(query, customCountquery, pageRequest, getRowMapper()));
    }

    @Override
    public <R> JdbcPage<R> fetchPage(JdbcPageRequest pageRequest) {
        this.rowMapperRequired();
        this.withPageRequest(pageRequest);
        this.pageRequestRequired();
        return execute(() -> fetchPage(query, customCountquery, pageRequest, getRowMapper()));
    }

    @Override
    public <R> JdbcPage<R> fetchPage(JdbcPageRequest pageRequest, Class<R> resultType) {
        this.resultTypeRequired(resultType);
        this.withPageRequest(pageRequest);
        this.pageRequestRequired();
        this.createRowMapperIfnotExists(resultType);
        return execute(() -> fetchPage(query, customCountquery, pageRequest, getRowMapper()));
    }

    protected void pageRequestRequired() {
        Assert.notNull(pageRequest, "JdbcPageRequest required!");
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        throw new JdbcPaginationException("You have to implement the dialect for pagination");
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        throw new JdbcPaginationException("You have to implement the dialect for pagination");
    }

}
