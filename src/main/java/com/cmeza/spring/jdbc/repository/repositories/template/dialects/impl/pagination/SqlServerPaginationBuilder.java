package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.pagination;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

import java.sql.Types;
import java.util.Collections;

public class SqlServerPaginationBuilder extends DefaultPaginationBuilder {
    private static final String OFFSET_PARAM_NAME = "__offset__";
    private static final String LIMIT_PARAM_NAME = "__limit__";

    public SqlServerPaginationBuilder(String query, Impl impl) {
        super(query, impl);
    }

    @Override
    protected String convertToPageSql(String sql) throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse(sql);
        SelectBody body = ((Select) stmt).getSelectBody();
        if (body instanceof PlainSelect) {
            PlainSelect ps = (PlainSelect) body;
            if (ps.getOrderByElements() == null || ps.getOrderByElements().isEmpty()) {
                OrderByElement orderByElement = new OrderByElement();
                orderByElement.withExpression(new Column("1"));
                ps.withOrderByElements(Collections.singletonList(orderByElement));
                sql = ps.toString();
            }
        }

        StringBuilder sb = new StringBuilder(sql.length() + 50);
        sb.append(sql)
                .append(" offset ").append(":").append(OFFSET_PARAM_NAME)
                .append(" rows fetch next ").append(":").append(LIMIT_PARAM_NAME).append(" rows only");
        return sb.toString();
    }

    @Override
    protected void preparePageParams(JdbcPageRequest pageRequest) {
        withParameter(OFFSET_PARAM_NAME, pageRequest.getOffset(), Types.INTEGER);
        withParameter(LIMIT_PARAM_NAME, pageRequest.getPageSize(), Types.INTEGER);
    }
}
