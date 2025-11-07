package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcFunctionProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcFunctionConverter implements Converter<String, JdbcFunctionProperties> {

    @Override
    public JdbcFunctionProperties convert(@NonNull String source) {
        try {
            return new JdbcFunctionProperties().setName(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid name of JdbcFunction: " + source, e
            );
        }
    }
}
