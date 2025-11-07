package com.cmeza.spring.jdbc.repository.utils;

import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.lang.NonNull;

public class JdbcBeanPropertySqlParameterSource extends AbstractSqlParameterSource implements JdbcBeanPropertiesMapping {

    private final JdbcBeanPropertyManager jdbcBeanPropertyManager;

    public JdbcBeanPropertySqlParameterSource(Object object) {
        this.jdbcBeanPropertyManager = new JdbcBeanPropertyManager(object);
    }

    public boolean hasValue(String paramName) {
        return this.jdbcBeanPropertyManager.hasValue(paramName);
    }

    public Object getValue(String paramName) throws IllegalArgumentException {
        return this.jdbcBeanPropertyManager.getValue(paramName);
    }

    @Override
    public int getSqlType(String paramName) {
        int sqlType = super.getSqlType(paramName);
        if (sqlType != Integer.MIN_VALUE) {
            return sqlType;
        } else {
            return jdbcBeanPropertyManager.getSqlType(paramName);
        }
    }

    @Override
    public void addMappingDefinition(MappingDefinition mappingDefinition) {
        jdbcBeanPropertyManager.addMappingDefinition(mappingDefinition);
    }

    @NonNull
    @Override
    public String[] getParameterNames() {
        return this.jdbcBeanPropertyManager.getReadablePropertyNames();
    }

}
