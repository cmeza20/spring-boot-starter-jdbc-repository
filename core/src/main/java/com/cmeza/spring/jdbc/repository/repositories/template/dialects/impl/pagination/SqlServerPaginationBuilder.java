package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination;

import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.sql.Types;
import java.util.Collections;

public class SqlServerPaginationBuilder extends DefaultPaginationBuilder {
    private static final String OFFSET_PARAM_NAME = "__offset__";
    private static final String LIMIT_PARAM_NAME = "__limit__";

    public SqlServerPaginationBuilder(String query, Impl impl) {
        super(query, impl);
    }

    public SqlServerPaginationBuilder(JdbcSelectFactory selectBuilder, Impl impl) {
        super(selectBuilder, impl);
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse(sql);
        if (stmt instanceof PlainSelect) {
            PlainSelect ps = (PlainSelect) stmt;
            if (ps.getOrderByElements() == null || ps.getOrderByElements().isEmpty()) {
                OrderByElement orderByElement = new OrderByElement();
                orderByElement.withExpression(new Column("1"));
                ps.withOrderByElements(Collections.singletonList(orderByElement));
                sql = ps.toString();
            }
        }

        return sql +
                " offset " + ":" + OFFSET_PARAM_NAME +
                " rows fetch next " + ":" + LIMIT_PARAM_NAME + " rows only";
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        withMapping(OFFSET_PARAM_NAME, OFFSET_PARAM_NAME, Types.INTEGER);
        withMapping(LIMIT_PARAM_NAME, LIMIT_PARAM_NAME, Types.INTEGER);
        withParameter(OFFSET_PARAM_NAME, pageRequest.getOffset(), Types.INTEGER);
        withParameter(LIMIT_PARAM_NAME, pageRequest.getPageSize(), Types.INTEGER);
    }
}
