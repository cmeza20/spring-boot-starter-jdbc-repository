package com.cmeza.spring.jdbc.repository.mappers;

import com.cmeza.spring.jdbc.repository.resolvers.JdbcProjectionSupport;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcProjectionSupportImpl;
import lombok.Getter;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Getter
public abstract class JdbcRowMapper<T> implements RowMapper<T> {
    private final Class<T> mappedClass;
    private final BeanPropertyRowMapper<T> beanPropertyRowMapper;
    private ProjectionJdbcRowMapper<T> projectionJdbcRowMapper;
    private JdbcProjectionSupport<T, T> projectionSupport;
    private boolean isDefaultProjectionSupport;

    protected JdbcRowMapper(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        this.beanPropertyRowMapper = new BeanPropertyRowMapper<>(this.mappedClass);
    }

    public static <T> JdbcRowMapper<T> newInstance(Class<T> mappedClass) {
        return new ClassJdbcRowMapper<>(mappedClass);
    }

    @Override
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        T obj;
        if (Objects.nonNull(projectionSupport)) {
            if (isDefaultProjectionSupport) {
                obj = projectionJdbcRowMapper.mapRow(rs, rowNumber);
            } else if (projectionSupport.overrideMapRow()) {
                obj = projectionSupport.constructMappedInstance(rs, new BeanWrapperImpl());
            } else {
                obj = beanPropertyRowMapper.mapRow(rs, rowNumber);
            }
            projectionSupport.mapRow(obj, rs, rowNumber);
        } else {
            obj = beanPropertyRowMapper.mapRow(rs, rowNumber);
        }
        return obj;
    }

    public JdbcRowMapper<T> init(JdbcProjectionSupport<T, T> projectionSupport) {
        if (this.mappedClass.isInterface()) {
            this.projectionSupport = Objects.nonNull(projectionSupport) ? projectionSupport : new JdbcProjectionSupportImpl<>();
            this.isDefaultProjectionSupport = Objects.isNull(projectionSupport);
            this.projectionJdbcRowMapper = new ProjectionJdbcRowMapper<>(this.mappedClass);
        }
        return this;
    }
}
