package com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcBuilder;
import org.springframework.jdbc.core.RowMapper;

public interface QueryImplementation<T extends JdbcBuilder<?>> {
    Object execute(T builder, RowMapper<?> rowMapper, Class<?> resultType);
}
