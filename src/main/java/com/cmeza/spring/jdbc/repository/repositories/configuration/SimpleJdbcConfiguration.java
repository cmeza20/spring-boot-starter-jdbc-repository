package com.cmeza.spring.jdbc.repository.repositories.configuration;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.repositories.parameters.ParameterDefinition;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import lombok.Builder;
import lombok.Getter;
import org.springframework.jdbc.core.RowMapper;

@Getter
@Builder(builderClassName = "Builder")
public class SimpleJdbcConfiguration implements JdbcConfiguration {

    private final JdbcRepositoryTemplate jdbcTemplate;
    private final ParameterDefinition[] parameters;
    private final RowMapper<?> rowMapper;
    private final TypeMetadata typeMetadata;
    private final Class<?> targetClass;
    private final String configKey;
    private final boolean loggeable;
    private final boolean needRowMapper;
}
