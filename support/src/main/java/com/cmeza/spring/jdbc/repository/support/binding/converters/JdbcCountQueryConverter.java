package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcCountQueryProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcCountQueryConverter implements Converter<String, JdbcCountQueryProperties> {

    @Override
    public JdbcCountQueryProperties convert(@NonNull String source) {
        try {
            return new JdbcCountQueryProperties().setTable(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid table of JdbcCountQuery: " + source, e
            );
        }
    }
}
