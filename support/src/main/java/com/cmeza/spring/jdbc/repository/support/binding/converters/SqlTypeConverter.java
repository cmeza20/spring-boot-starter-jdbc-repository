package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.binding.SqlType;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;
import java.sql.Types;

@ConfigurationPropertiesBinding
public class SqlTypeConverter implements Converter<String, SqlType> {

    @Override
    public SqlType convert(@NonNull String source) {
        try {
            Field field = Types.class.getField(source.toUpperCase());
            int v = field.getInt(null);
            return new SqlType(v);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "java.sql.Types invalid: " + source, e
            );
        }
    }
}
