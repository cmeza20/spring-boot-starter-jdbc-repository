package com.cmeza.spring.jdbc.repository.resolvers;

import org.springframework.beans.TypeConverter;

import java.sql.ResultSet;
import java.sql.SQLException;


public interface JdbcProjectionSupport<T, C extends T> extends JdbcProjectionData<T, C> {

    T constructMappedInstance(ResultSet rs, TypeConverter tc) throws SQLException;

    default void mapRow(C obj, ResultSet rs, int rowNumber) throws SQLException {
    }

    default boolean overrideMapRow() {
        return false;
    }
}
