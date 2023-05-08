package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;

import java.sql.Types;

public class OraclePaginationBuilder extends DefaultPaginationBuilder {
    private static final String START_ROW_PARAM_NAME = "__start_row__";
    private static final String END_ROW_PARAM_NAME = "__end_row__";

    public OraclePaginationBuilder(String query, Impl impl) {
        super(query, impl);
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        StringBuilder sb = new StringBuilder(sql.length() + 100);
        sb.append("select * from (select tmp_page.*, ROWNUM r_id from (")
                .append(sql)
                .append(") tmp_page where ROWNUM<=")
                .append(":").append(END_ROW_PARAM_NAME)
                .append(")").append(" where r_id>")
                .append(":").append(START_ROW_PARAM_NAME);
        return sb.toString();
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        withParameter(START_ROW_PARAM_NAME, pageRequest.getOffset(), Types.NUMERIC);
        withParameter(END_ROW_PARAM_NAME, pageRequest.getPageSize(), Types.NUMERIC);
    }
}
