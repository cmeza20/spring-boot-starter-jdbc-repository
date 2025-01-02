package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcRawQuery;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcQuery;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.QueryReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcQueryBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class JdbcQueryExecutor extends AbstractJdbcExecutor<JdbcQueryBuilder> {

    private JdbcQuery jdbcQuery;
    private JdbcRawQuery jdbcRawQuery;

    public JdbcQueryExecutor(JdbcQuery jdbcQuery) {
        Assert.notNull(jdbcQuery.table(), "Table name required");
        Assert.hasLength(jdbcQuery.table(), "Table name is empty");
        Assert.notEmpty(jdbcQuery.columns(), "Columns name required");
        Assert.hasLength(jdbcQuery.alias(), "Alias required");
        Assert.state(!StringUtils.hasLength(jdbcQuery.where()) || !jdbcQuery.where().toUpperCase().contains("WHERE"), "Query 'WHERE' command not required");
        this.jdbcQuery = jdbcQuery;
    }

    public JdbcQueryExecutor(JdbcRawQuery jdbcRawQuery) {
        String query = jdbcRawQuery.value();
        Assert.hasLength(query, "Query required");
        Assert.state(StringUtils.hasLength(query) && query.toUpperCase().contains("SELECT"), "Query 'SELECT' command required");
        this.jdbcRawQuery = jdbcRawQuery;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.QUERY;
    }

    @Override
    protected Object execute(ReturnType returnType, JdbcQueryBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        QueryReturnType queryReturnType = QueryReturnType.from(returnType);
        if (queryReturnType.equals(QueryReturnType.MAP) && !(rowMapper instanceof ColumnMapRowMapper)) {
            Assert.isNull(rowMapper, configuration.getConfigKey() + " - Map is not compatible with RowMapper");
        }
        return queryReturnType.execute(jdbcBuilder, rowMapper, typeMetadata.getArgumentClass());
    }

    @Override
    protected JdbcQueryBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {
        if (Objects.nonNull(jdbcQuery)) {
            JdbcSelectFactory selectBuilder = jdbcTemplate.factories().select(jdbcQuery.table())
                    .withCatalog(jdbcQuery.catalog())
                    .withSchema(jdbcQuery.schema())
                    .withAlias(jdbcQuery.alias())
                    .withColumns(jdbcQuery.columns())
                    .withWhere(jdbcQuery.where())
                    .withOrderBy(jdbcQuery.orderBy())
                    .withGroupBy(jdbcQuery.groupBy())
                    .withJoinTable(configuration.getJoinTables());

            return jdbcTemplate.query(selectBuilder)
                    .withKey(configuration.getConfigKey())
                    .loggeable(configuration.isLoggeable());
        } else {
            return jdbcTemplate.query(jdbcRawQuery.value())
                    .withKey(configuration.getConfigKey())
                    .loggeable(configuration.isLoggeable());
        }

    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        String configKey = jdbcConfiguration.getConfigKey();
        Assert.isTrue(!typeMetadata.isVoid(), configKey + " - Void return type is not supported, use @JdbcExecute annotation instead");
        Assert.isTrue(!typeMetadata.isEnum(), configKey + " - Enum return type is not supported");
        Assert.isTrue(!typeMetadata.isMapEntry(), configKey + " - Map.Entry return type is not supported");
        Assert.isTrue(!typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), JdbcPage.class), configKey + " - JdbcPage return type is not supported, use @JdbcPagination annotation instead");
    }
}
