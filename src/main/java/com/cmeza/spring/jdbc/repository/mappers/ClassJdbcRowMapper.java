package com.cmeza.spring.jdbc.repository.mappers;

public class ClassJdbcRowMapper<T> extends JdbcRowMapper<T> {
    protected ClassJdbcRowMapper(Class<T> mappedClass) {
        super(mappedClass);
    }
}
