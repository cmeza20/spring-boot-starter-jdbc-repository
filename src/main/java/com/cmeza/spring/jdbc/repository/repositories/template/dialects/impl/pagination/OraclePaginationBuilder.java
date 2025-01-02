package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;

import java.sql.Types;

public class OraclePaginationBuilder extends DefaultPaginationBuilder {
    private static final String OFFSET_ROW_PARAM_NAME = "__offset__";
    private static final String LIMIT_ROW_PARAM_NAME = "__limit__";

    public OraclePaginationBuilder(String query, Impl impl) {
        super(query, impl);
    }

    public OraclePaginationBuilder(JdbcSelectFactory selectBuilder, Impl impl) {
        super(selectBuilder, impl);
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        return new StringBuilder(sql.length() + 100)
                .append("select * from (select tmp_page.*, ROWNUM r_id from (")
                .append(sql)
                .append(") tmp_page )")
                .append(" where r_id > ").append(":").append(OFFSET_ROW_PARAM_NAME)
                .append(" AND r_id <= (")
                .append(":").append(OFFSET_ROW_PARAM_NAME)
                .append(" + ")
                .append(":").append(LIMIT_ROW_PARAM_NAME)
                .append(")")
                .toString();
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        withMapping(OFFSET_ROW_PARAM_NAME, OFFSET_ROW_PARAM_NAME, Types.NUMERIC);
        withMapping(LIMIT_ROW_PARAM_NAME, LIMIT_ROW_PARAM_NAME, Types.NUMERIC);
        withParameter(OFFSET_ROW_PARAM_NAME, pageRequest.getOffset(), Types.NUMERIC);
        withParameter(LIMIT_ROW_PARAM_NAME, pageRequest.getPageSize(), Types.NUMERIC);
    }
}
