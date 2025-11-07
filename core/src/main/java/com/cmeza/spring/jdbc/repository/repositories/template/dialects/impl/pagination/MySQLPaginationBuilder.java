package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination;

import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;

import java.sql.Types;

public class MySQLPaginationBuilder extends DefaultPaginationBuilder {
    private static final String OFFSET_PARAM_NAME = "__offset__";
    private static final String LIMIT_PARAM_NAME = "__limit__";

    public MySQLPaginationBuilder(String query, Impl impl) {
        super(query, impl);
    }

    public MySQLPaginationBuilder(JdbcSelectFactory selectBuilder, Impl impl) {
        super(selectBuilder, impl);
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        return new StringBuilder(sql.length() + 50)
                .append(sql)
                .append(" limit ")
                .append(":").append(OFFSET_PARAM_NAME)
                .append(", ")
                .append(":").append(LIMIT_PARAM_NAME)
                .toString();
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        withMapping(OFFSET_PARAM_NAME, OFFSET_PARAM_NAME, Types.NUMERIC);
        withMapping(LIMIT_PARAM_NAME, LIMIT_PARAM_NAME, Types.NUMERIC);
        withParameter(OFFSET_PARAM_NAME, pageRequest.getOffset(), Types.NUMERIC);
        withParameter(LIMIT_PARAM_NAME, pageRequest.getPageSize(), Types.NUMERIC);
    }
}
