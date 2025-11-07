package com.cmeza.spring.jdbc.repository.mappers;

public class DefaultJdbcRowMapper<T> extends JdbcRowMapper<T> {
    protected DefaultJdbcRowMapper(Class<T> mappedClass) {
        super(mappedClass);
    }
}
