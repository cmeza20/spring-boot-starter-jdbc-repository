package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcInsertProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcInsertConverter implements Converter<String, JdbcInsertProperties> {

    @Override
    public JdbcInsertProperties convert(@NonNull String source) {
        try {
            return new JdbcInsertProperties().setTable(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid table of JdbcInsert: " + source, e
            );
        }
    }
}
