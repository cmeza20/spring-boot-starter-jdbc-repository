package com.cmeza.spring.jdbc.repository.resolvers;

import lombok.AllArgsConstructor;
import org.springframework.beans.TypeConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class JdbcProjectionSupportImpl<T> implements JdbcProjectionSupport<T, T> {

    @Override
    public T constructMappedInstance(ResultSet rs, TypeConverter tc) throws SQLException {
        return null;
    }

    @Override
    public boolean overrideMapRow() {
        return true;
    }
}
