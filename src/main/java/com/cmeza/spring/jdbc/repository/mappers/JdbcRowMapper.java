package com.cmeza.spring.jdbc.repository.mappers;

import lombok.Getter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public abstract class JdbcRowMapper<T> implements RowMapper<T> {
    private final Class<T> mappedClass;
    private final BeanPropertyRowMapper<T> beanPropertyRowMapper;

    protected JdbcRowMapper(Class<T> mappedClass) {
        Assert.isTrue(!mappedClass.isInterface(), "The mapped class must not be an interface");
        this.mappedClass = mappedClass;
        this.beanPropertyRowMapper = new BeanPropertyRowMapper<>(this.mappedClass);
    }

    protected void mapClass(ResultSet rs, T entity, int rowNumber) throws SQLException {
    }

    public static <T> JdbcRowMapper<T> newInstance(Class<T> mappedClass) {
        return new DefaultJdbcRowMapper<>(mappedClass);
    }

    @Override
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        T entity = beanPropertyRowMapper.mapRow(rs, rowNumber);
        Assert.notNull(entity, "The result should not be null");
        mapClass(rs, entity, rowNumber);
        return entity;
    }
}
