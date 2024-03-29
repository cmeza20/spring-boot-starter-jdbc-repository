package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcQuery;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.QueryReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcQueryBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class JdbcQueryExecutor extends AbstractJdbcExecutor<JdbcQueryBuilder> {

    private final JdbcQuery jdbcQuery;

    public JdbcQueryExecutor(JdbcQuery jdbcQuery) {
        String query = jdbcQuery.value();
        Assert.notNull(query, "Query required");
        Assert.hasLength(query, "Query is empty");
        Assert.state(StringUtils.hasLength(query) && query.toUpperCase().contains("SELECT"), "Query 'SELECT' command required");
        this.jdbcQuery = jdbcQuery;
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
        return jdbcTemplate.query(jdbcQuery.value())
                .withKey(configuration.getConfigKey())
                .loggeable(configuration.isLoggeable());
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        String configKey = jdbcConfiguration.getConfigKey();
        Assert.isTrue(!typeMetadata.isVoid(), configKey + " - Void return type is not supported, use @JdbcUpdate annotation instead");
        Assert.isTrue(!typeMetadata.isEnum(), configKey + " - Enum return type is not supported");
        Assert.isTrue(!typeMetadata.isMapEntry(), configKey + " - Map.Entry return type is not supported");
        Assert.isTrue(!typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), JdbcPage.class), configKey + " - JdbcPage return type is not supported, use @JdbcPagination annotation instead");
    }
}
