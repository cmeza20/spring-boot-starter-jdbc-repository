package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcPagination;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.PaginationReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.parameters.ParameterDefinition;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class JdbcPaginationExecutor extends AbstractJdbcExecutor<JdbcPaginationBuilder> {

    private final JdbcPagination jdbcPagination;

    public JdbcPaginationExecutor(JdbcPagination jdbcPagination) {
        String query = jdbcPagination.value();
        String countQuery = jdbcPagination.countQuery();
        Assert.notNull(query, "Query required");
        Assert.hasLength(query, "Query is empty");
        Assert.state(query.toUpperCase().contains("SELECT"), "Query 'SELECT' command required");
        if (StringUtils.hasLength(countQuery)) {
            Assert.state(countQuery.toUpperCase().contains("SELECT"), "Count Query 'SELECT' command required");
        }
        this.jdbcPagination = jdbcPagination;
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
        return jdbcTemplate
                .pagination(jdbcPagination.value())
                .withCountQuery(jdbcPagination.countQuery())
                .loggeable(configuration.isLoggeable());
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
