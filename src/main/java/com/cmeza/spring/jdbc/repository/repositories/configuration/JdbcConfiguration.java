package com.cmeza.spring.jdbc.repository.repositories.configuration;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.repositories.definitions.*;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import org.springframework.jdbc.core.RowMapper;

public interface JdbcConfiguration {
    String getConfigKey();

    JdbcRepositoryTemplate getJdbcTemplate();

    ParameterDefinition[] getParameters();

    RowMapper<?> getRowMapper();

    TypeMetadata getTypeMetadata();

    Class<?> getTargetClass();

    boolean isLoggeable();

    boolean isNeedRowMapper();

    MappingDefinition[] getMappings();

    JoinTableDefinition[] getJoinTables();

    TableDefinition getFromTable();

    QueryDefinition getCountQuery();

}
