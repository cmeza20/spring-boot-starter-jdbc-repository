package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

public interface JdbcBatchUpdateBuilder extends JdbcBuilder<JdbcBatchUpdateBuilder> {
    int[] execute();
}
