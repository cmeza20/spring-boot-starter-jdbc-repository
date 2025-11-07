package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawUpdateProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcRawUpdateConverter implements Converter<String, JdbcRawUpdateProperties> {

    @Override
    public JdbcRawUpdateProperties convert(@NonNull String source) {
        try {
            return new JdbcRawUpdateProperties().setValue(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid value of JdbcRawUpdate: " + source, e
            );
        }
    }
}
