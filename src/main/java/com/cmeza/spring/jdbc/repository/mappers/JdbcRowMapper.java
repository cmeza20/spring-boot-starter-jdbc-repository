package com.cmeza.spring.jdbc.repository.mappers;

import com.cmeza.spring.jdbc.repository.resolvers.JdbcProjectionSupport;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.TypeConverter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class JdbcRowMapper<T> extends BeanPropertyRowMapper<T> {
    private JdbcProjectionSupport<T, T> projectionSupport;

    protected JdbcRowMapper(Class<T> mappedClass) {
        super(mappedClass);
    }

    public static <T> JdbcRowMapper<T> newInstance(Class<T> mappedClass) {
        return new ClassJdbcRowMapper<>(mappedClass);
    }

    @Override
    protected T constructMappedInstance(ResultSet rs, TypeConverter tc) throws SQLException {
        if (Objects.nonNull(projectionSupport)) {
            return projectionSupport.constructMappedInstance(rs, tc);
        }
        return super.constructMappedInstance(rs, tc);
    }

    @Override
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        T obj;
        if (Objects.nonNull(projectionSupport)) {
            if (projectionSupport.overrideMapRow()) {
                obj = constructMappedInstance(rs, new BeanWrapperImpl());
            } else {
                obj = super.mapRow(rs, rowNumber);
            }
            projectionSupport.mapRow(obj, rs, rowNumber);
        } else {
            obj = super.mapRow(rs, rowNumber);
        }
        return obj;
    }

    public JdbcRowMapper<T> setProjectionSupport(JdbcProjectionSupport<T, T> projectionSupport) {
        if (Objects.nonNull(projectionSupport)) {
            this.projectionSupport = projectionSupport;
            this.initialize(projectionSupport.getProjectionImplClass());
        }
        return this;
    }
}
