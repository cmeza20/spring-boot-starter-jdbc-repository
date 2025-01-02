package com.cmeza.spring.jdbc.repository.repositories.utils;

import com.cmeza.spring.jdbc.repository.repositories.definitions.MappingDefinition;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JdbcMapSqlParameterSource extends AbstractSqlParameterSource implements JdbcBeanPropertiesMapping {
    private final Map<String, Object> values = new LinkedHashMap<>();
    private final Map<String, MappingDefinition> additionalProperties = new HashMap<>();
    private String[] propertyNames;

    public JdbcMapSqlParameterSource() {
    }

    public JdbcMapSqlParameterSource(String paramName, @Nullable Object value) {
        this.addValue(paramName, value);
    }

    public JdbcMapSqlParameterSource(@Nullable Map<String, ?> values) {
        this.addValues(values);
    }

    @Override
    public boolean hasValue(String paramName) {
        if (additionalProperties.containsKey(paramName)) {
            return true;
        }
        return this.values.containsKey(paramName);
    }

    @Override
    public Object getValue(String paramName) throws IllegalArgumentException {
        if (!this.hasValue(paramName)) {
            throw new IllegalArgumentException("No value registered for key '" + paramName + "'");
        } else {
            if (additionalProperties.containsKey(paramName)) {
                return additionalProperties.get(paramName).getValue();
            }
            return this.values.get(paramName);
        }
    }

    @Override
    public String[] getParameterNames() {
        if (this.propertyNames == null) {
            this.propertyNames = StringUtils.toStringArray(this.values.keySet());
        }

        return this.propertyNames;
    }

    public Map<String, Object> getValues() {
        Map<String, Object> result = new HashMap<>(this.values);
        additionalProperties.forEach((key, value) -> result.put(key, value.getValue()));
        return result;
    }

    @Override
    public int getSqlType(String paramName) {
        if (additionalProperties.containsKey(paramName)) {
            return additionalProperties.get(paramName).getType();
        }
        return super.getSqlType(paramName);
    }

    @Override
    public void addMappingDefinition(MappingDefinition mappingDefinition) {
        mappingDefinition.setValue(getValue(mappingDefinition.getFrom()));
        additionalProperties.put(mappingDefinition.getTo(), mappingDefinition);
    }

    public JdbcMapSqlParameterSource addValues(@Nullable Map<String, ?> values) {
        if (values != null) {
            values.forEach((key, value) -> {
                this.values.put(key, value);
                if (value instanceof SqlParameterValue) {
                    this.registerSqlType(key, ((SqlParameterValue) value).getSqlType());
                }

            });
        }

        return this;
    }

    public JdbcMapSqlParameterSource addValue(String paramName, @Nullable Object value) {
        Assert.notNull(paramName, "Parameter name must not be null");
        this.values.put(paramName, value);
        if (value instanceof SqlParameterValue) {
            this.registerSqlType(paramName, ((SqlParameterValue) value).getSqlType());
        }

        return this;
    }

    public JdbcMapSqlParameterSource addValue(String paramName, @Nullable Object value, int sqlType) {
        Assert.notNull(paramName, "Parameter name must not be null");
        this.values.put(paramName, value);
        this.registerSqlType(paramName, sqlType);
        return this;
    }


}
