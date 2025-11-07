package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawPagination;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcPagination;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.support.definitions.ParameterDefinition;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.PaginationReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.support.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class JdbcPaginationExecutor extends AbstractJdbcExecutor<JdbcPaginationBuilder> {

    private JdbcPagination jdbcPagination;
    private JdbcRawPagination jdbcRawPagination;

    public JdbcPaginationExecutor(JdbcPagination jdbcPagination) {
        Assert.notNull(jdbcPagination.table(), "Table name required");
        Assert.hasLength(jdbcPagination.table(), "Table name is empty");
        Assert.notNull(jdbcPagination.columns(), "Columns name required");
        Assert.notEmpty(jdbcPagination.columns(), "Columns name is empty");
        Assert.notNull(jdbcPagination.alias(), "Alias required");
        Assert.hasLength(jdbcPagination.alias(), "Alias is empty");
        Assert.state(!StringUtils.hasLength(jdbcPagination.where()) || !jdbcPagination.where().toUpperCase().contains("WHERE"), "Query 'WHERE' command not required");
        this.jdbcPagination = jdbcPagination;
    }

    public JdbcPaginationExecutor(JdbcRawPagination jdbcRawPagination) {
        String query = jdbcRawPagination.value();
        Assert.notNull(query, "Query required");
        Assert.hasLength(query, "Query is empty");
        Assert.state(query.toUpperCase().contains("SELECT"), "Query 'SELECT' command required");
        this.jdbcRawPagination = jdbcRawPagination;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.PAGE;
    }

    @Override
    protected Object execute(ReturnType returnType, JdbcPaginationBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        PaginationReturnType paginationReturnType = PaginationReturnType.from(returnType);
        return paginationReturnType.execute(jdbcBuilder, rowMapper, typeMetadata.getArgumentClass());
    }

    @Override
    protected JdbcPaginationBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {

        if (Objects.nonNull(jdbcPagination)) {
            JdbcSelectFactory selectBuilder = jdbcTemplate.factories().select(jdbcPagination.table())
                    .withCatalog(jdbcPagination.catalog())
                    .withSchema(jdbcPagination.schema())
                    .withAlias(jdbcPagination.alias())
                    .withColumns(jdbcPagination.columns())
                    .withWhere(jdbcPagination.where())
                    .withOrderBy(jdbcPagination.orderBy())
                    .withGroupBy(jdbcPagination.groupBy())
                    .withJoinTable(configuration.getJoinTables());

            return jdbcTemplate.pagination(selectBuilder)
                    .withCountQuery(configuration.getCountQuery())
                    .withKey(configuration.getConfigKey())
                    .loggable(configuration.isLoggable());
        } else {
            return jdbcTemplate.pagination(jdbcRawPagination.value())
                    .withCountQuery(configuration.getCountQuery())
                    .withKey(configuration.getConfigKey())
                    .loggable(configuration.isLoggable());
        }
    }

    @Override
    protected void eachParameter(JdbcPaginationBuilder builder, ParameterDefinition parameterDefinition, Object obj) {
        if (parameterDefinition.isPageRequest()) {
            builder.withPageRequest((JdbcPageRequest) obj);
        }
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        Assert.isTrue(typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), JdbcPage.class), jdbcConfiguration.getConfigKey() + " - Only supports the JdbcPage.class return type");
    }

}
