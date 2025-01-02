package com.cmeza.spring.jdbc.repository.repositories.executors.definition;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcInsert;
import com.cmeza.spring.jdbc.repository.repositories.configuration.JdbcConfiguration;
import com.cmeza.spring.jdbc.repository.repositories.executors.AbstractJdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ExecutorType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.providers.InsertReturnType;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcInsertBuilder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

public class JdbcInsertExecutor extends AbstractJdbcExecutor<JdbcInsertBuilder> {

    private static final String SUPPORT_MESSAGE = " - @JdbcInsert only supports the following response types: Integer, Integer[], KeyHolder";
    private final JdbcInsert jdbcInsert;

    public JdbcInsertExecutor(JdbcInsert jdbcInsert) {
        Assert.notNull(jdbcInsert.table(), "Table name required");
        Assert.hasLength(jdbcInsert.table(), "Table name is empty");
        this.jdbcInsert = jdbcInsert;
    }

    @Override
    public ExecutorType getExecuteType() {
        return ExecutorType.INSERT;
    }


    @Override
    protected Object execute(ReturnType returnType, JdbcInsertBuilder jdbcBuilder, TypeMetadata typeMetadata, JdbcConfiguration configuration, RowMapper<?> rowMapper, boolean isBatch) {
        InsertReturnType insertReturnType = InsertReturnType.from(returnType);
        if ((insertReturnType.equals(InsertReturnType.INTEGER) || insertReturnType.equals(InsertReturnType.KEY_HOLDER)) && isBatch) {
            throw new UnsupportedOperationException(configuration.getConfigKey() + " - return type Integer does not support batch parameters, use only one parameter");
        }
        return insertReturnType.execute(jdbcBuilder);
    }

    @Override
    protected JdbcInsertBuilder bindBuilder(JdbcRepositoryTemplate jdbcTemplate, JdbcConfiguration configuration) {
        return jdbcTemplate.insert(jdbcInsert.table())
                .withCatalog(jdbcInsert.catalog())
                .withSchema(jdbcInsert.schema())
                .withColumns(jdbcInsert.columns())
                .withGeneratedKeyColumns(jdbcInsert.generatedKeyColumns())
                .withAccessTableColumnMetaData(jdbcInsert.accessTableColumnMetaData())
                .withOverrideIncludeSynonymsDefault(jdbcInsert.overrideIncludeSynonymsDefault())
                .withKey(configuration.getConfigKey())
                .loggeable(configuration.isLoggeable());
    }

    @Override
    public void validateConfiguration(JdbcConfiguration jdbcConfiguration) {
        TypeMetadata typeMetadata = jdbcConfiguration.getTypeMetadata();
        Assert.isTrue(
                typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), int.class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), int[].class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), Integer.class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), Integer[].class) ||
                        typeMetadata.isAssignableFrom(typeMetadata.getRawClass(), KeyHolder.class), jdbcConfiguration.getConfigKey() + SUPPORT_MESSAGE);
    }

}
