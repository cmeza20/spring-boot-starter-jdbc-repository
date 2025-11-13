package com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts;

import com.cmeza.spring.jdbc.repository.support.exceptions.JdbcPaginationException;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageImpl;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import com.cmeza.spring.jdbc.repository.utils.JdbcPaginationUtils;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractPaginationBuilder extends AbstractJdbcBuilder<JdbcPaginationBuilder> implements JdbcPaginationBuilder {
    private static final SelectItem<?> COUNT_SELECT_ITEMS = SelectItem.from(new Column("count(1)"));
    private static final Map<Integer, String> countSqlCache = new LinkedHashMap<>();
    private static final Map<Integer, String> pageSqlCache = new LinkedHashMap<>();
    private final ResultSetExtractor<Long> resultSetExtractor = rs -> rs.next() ? rs.getLong(1) : 0L;
    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;

    private String dialectName;

    protected AbstractPaginationBuilder(Impl impl) {
        super(impl);
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
        this.dialectName = impl.getDatabaseMetaData().getDatabaseProductName();
    }

    public <R> JdbcPage<R> fetchPage(String sql, String countSql, JdbcPageRequest pageRequest, RowMapper<R> rowMapper) {
        return execute(() -> {
            int hashCode = getHashCode(sql);

            try {
                Long total = 0L;
                String csql = getCountSql(sql, countSql, hashCode);
                String psql = getPageSql(sql, hashCode);

                if (loggable) {
                    AbstractJdbcBuilder.log.info("| Query: [{}]", psql);
                    AbstractJdbcBuilder.log.info("| Count query: [{}]", csql);
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
        });
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

            JdbcPaginationUtils.cleanSelect(stmt);

            if (stmt instanceof PlainSelect a && JdbcPaginationUtils.isSimpleCountSql(a)) {
                PlainSelect ps = (PlainSelect) stmt;
                ps.withSelectItems(COUNT_SELECT_ITEMS);
                csql = ps.toString();
            } else {
                Alias alias = new Alias("jdbc_alias", false);

                PlainSelect ps = new PlainSelect();
                ParenthesedSelect parenthesedSelect = new ParenthesedSelect();
                parenthesedSelect.setSelect((Select) stmt);
                parenthesedSelect.setAlias(alias);

                ps.setFromItem(parenthesedSelect);
                ps.withSelectItems(COUNT_SELECT_ITEMS);
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

    private int getHashCode(String sql) {
        return (dialectName + sql).hashCode();
    }

    protected abstract String convertToPageSql(String sql) throws JSQLParserException;

    protected abstract void preparePageParams(JdbcPageRequest pageRequest);
}
