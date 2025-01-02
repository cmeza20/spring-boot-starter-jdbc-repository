package com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;

public interface ExecuteImplementation<T extends JdbcGenericBuilder<?>> {
    Object execute(T builder);
}
