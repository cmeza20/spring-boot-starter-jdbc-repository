package com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts;

import com.cmeza.spring.jdbc.repository.repositories.exceptions.JdbcPaginationException;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageImpl;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcPaginationUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.springframework.jdbc.core.*;
import org.springframework.util.StringUtils;

import java.util.*;

public abstract class AbstractPaginationBuilder extends AbstractJdbcBuilder<JdbcPaginationBuilder> implements JdbcPaginationBuilder {
    private static final List<SelectItem> COUNT_SELECT_ITEMS = Collections.singletonList(new SelectExpressionItem(new Column("count(1)")));
    private final Map<Integer, String> countSqlCache = new LinkedHashMap<>();
    private final Map<Integer, String> pageSqlCache = new LinkedHashMap<>();
    private final ResultSetExtractor<Long> resultSetExtractor = rs -> rs.next() ? rs.getLong(1) : 0L;
    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;

    protected AbstractPaginationBuilder(Impl impl) {
        super(impl);
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    public <R> JdbcPage<R> fetchPage(String sql, String countSql, JdbcPageRequest pageRequest, RowMapper<R> rowMapper) {
        int hashCode = sql.hashCode();
        try {
            Long total = 0L;
            String csql = getCountSql(sql, countSql, hashCode);
            String psql = getPageSql(sql, hashCode);

            if (loggeable) {
                AbstractJdbcBuilder.log.info("| Query: [{}]", psql);
                AbstractJdbcBuilder.log.info("| Query count: [{}]", csql);
            }

            if (Objects.nonNull(csql)) {
                total = jdbcRepositoryTemplate.getJdbcOperations().query(jdbcRepositoryTemplate.getPreparedStatementCreator(csql, getParameterSources()), resultSetExtractor);
                if (Objects.nonNull(total) && total <= 0) {
                    return new JdbcPageImpl<>(total, pageRequest);
                }
            }

            this.preparePageParams(pageRequest);

            List<R> rows = jdbcRepositoryTemplate.getJdbcOperations().query(jdbcRepositoryTemplate.getPreparedStatementCreator(psql, getParameterSources()), rowMapper);

            return new JdbcPageImpl<>(rows, Objects.nonNull(total) ? total : 0L, pageRequest);
        } catch (JSQLParserException e) {
            throw new JdbcPaginationException(sql, e);
        }
    }

    protected String getCountSql(String sql, String countSql, int hashcode) throws JSQLParserException {
        String csql;
        if ((csql = countSqlCache.get(hashcode)) != null) {
            return csql;
        }

        synchronized (countSqlCache) {
            csql = countSql;
            if (StringUtils.hasLength(csql)) {
                countSqlCache.put(hashcode, csql);
                return csql;
            }

            Statement stmt = CCJSqlParserUtil.parse(sql);
            SelectBody body = ((Select) stmt).getSelectBody();
            JdbcPaginationUtils.cleanSelect(body);

            if (body instanceof PlainSelect && JdbcPaginationUtils.isSimpleCountSql((PlainSelect) body)) {
                PlainSelect ps = (PlainSelect) body;
                ps.setSelectItems(COUNT_SELECT_ITEMS);
                csql = ps.toString();
            } else {
                PlainSelect ps = new PlainSelect();
                SubSelect ss = new SubSelect();
                ss.setSelectBody(body);

                Alias alias = new Alias("jdbc_alias");
                alias.setUseAs(false);
                ps.setFromItem(ss);
                ps.setSelectItems(COUNT_SELECT_ITEMS);
                ss.setAlias(alias);
                csql = ps.toString();
            }

            countSqlCache.put(hashcode, csql);
            return csql;
        }
    }

    protected String getPageSql(String sql, int k) throws JSQLParserException {
        String psql;
        if ((psql = pageSqlCache.get(k)) != null) {
            return psql;
        }

        synchronized (pageSqlCache) {
            psql = convertToPageSql(sql);
            pageSqlCache.put(k, psql);
            return psql;
        }
    }

    protected abstract String convertToPageSql(String sql) throws JSQLParserException;

    protected abstract void preparePageParams(JdbcPageRequest pageRequest);
}
