package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination;

import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;

import java.sql.Types;

public class InformixPaginationBuilder extends DefaultPaginationBuilder {
    private static final String SKIP_PARAM_NAME = "__skip__";
    private static final String FIRST_PARAM_NAME = "__first__";

    public InformixPaginationBuilder(String query, Impl impl) {
        super(query, impl);
    }

    public InformixPaginationBuilder(JdbcSelectFactory selectBuilder, Impl impl) {
        super(selectBuilder, impl);
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        return new StringBuilder(sql.length() + 50)
                .append("select skip ")
                .append(":").append(SKIP_PARAM_NAME)
                .append(" first ").append(":").append(FIRST_PARAM_NAME)
                .append(" * from (")
                .append(sql)
                .append(") tmp_page")
                .toString();
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        withMapping(SKIP_PARAM_NAME, SKIP_PARAM_NAME, Types.NUMERIC);
        withMapping(FIRST_PARAM_NAME, FIRST_PARAM_NAME, Types.NUMERIC);
        withParameter(SKIP_PARAM_NAME, pageRequest.getOffset(), Types.NUMERIC);
        withParameter(FIRST_PARAM_NAME, pageRequest.getPageSize(), Types.NUMERIC);
    }
}
