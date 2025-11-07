package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcCallProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcCallConverter implements Converter<String, JdbcCallProperties> {

    @Override
    public JdbcCallProperties convert(@NonNull String source) {
        try {
            return new JdbcCallProperties().setValue(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid value of JdbcCall: " + source, e
            );
        }
    }
}
