package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination;

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

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        StringBuilder sb = new StringBuilder(sql.length() + 50);
        sb.append(sql)
                .append(" limit ")
                .append(":").append(OFFSET_PARAM_NAME)
                .append(", ")
                .append(":").append(LIMIT_PARAM_NAME);

        return sb.toString();
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        withParameter(OFFSET_PARAM_NAME, pageRequest.getOffset(), Types.NUMERIC);
        withParameter(LIMIT_PARAM_NAME, pageRequest.getPageSize(), Types.NUMERIC);
    }
}
