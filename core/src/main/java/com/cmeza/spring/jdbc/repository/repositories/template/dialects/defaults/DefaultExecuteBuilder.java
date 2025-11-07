package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcExecuteBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.parsers.ParsedJdbcSql;
import com.cmeza.spring.jdbc.repository.utils.JdbcNamedParameterUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DefaultExecuteBuilder extends AbstractJdbcBuilder<JdbcExecuteBuilder> implements JdbcExecuteBuilder {

    private final String query;
    private final JdbcRepositoryTemplate jdbcRepositoryTemplate;

    public DefaultExecuteBuilder(String query, Impl impl) {
        super(impl);
        this.query = query;
        this.jdbcRepositoryTemplate = impl.getJdbcRepositoryTemplate();
    }

    @Override
    public void printExtras(Logger logger) {
        logger.info("| Sql: [{}]", query);
    }

    @Override
    public int execute() {
        return execute(() -> jdbcRepositoryTemplate.getJdbcOperations().update(this.jdbcRepositoryTemplate.getPreparedStatementCreator(query, getParameterSources())));
    }

    @Override
    public int[] executeBatch() {
        if (getParameterSources().length == 0) {
            return execute(() -> jdbcRepositoryTemplate.getJdbcOperations().batchUpdate());
        } else {
            final ParsedJdbcSql parsedSql = jdbcRepositoryTemplate.getParsedJdbcSql(query);
            final PreparedStatementCreatorFactory pscf = jdbcRepositoryTemplate.getPreparedStatementCreatorFactory(parsedSql, new SqlParameterSource[] {getParameterSources()[0]});
            return execute(() -> jdbcRepositoryTemplate.getJdbcOperations().batchUpdate(pscf.getSql(), new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Object[] values = JdbcNamedParameterUtils.buildValueArray(parsedSql, getParameterSources()[i], null);
                    pscf.newPreparedStatementSetter(values).setValues(ps);
                }

                public int getBatchSize() {
                    return getParameterSources().length;
                }
            }));
        }
    }
}
