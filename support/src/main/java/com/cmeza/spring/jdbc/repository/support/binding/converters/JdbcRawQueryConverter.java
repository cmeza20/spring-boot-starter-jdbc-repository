package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawQueryProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcRawQueryConverter implements Converter<String, JdbcRawQueryProperties> {

    @Override
    public JdbcRawQueryProperties convert(@NonNull String source) {
        try {
            return new JdbcRawQueryProperties().setValue(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid value of JdbcRawQuery: " + source, e
            );
        }
    }
}
