package com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcBuilder;

public interface ExecuteImplementation<T extends JdbcBuilder<?>> {
    Object execute(T builder);
}
