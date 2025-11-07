package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcRawCountQueryProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcRawCountQueryConverter implements Converter<String, JdbcRawCountQueryProperties> {

    @Override
    public JdbcRawCountQueryProperties convert(@NonNull String source) {
        try {
            return new JdbcRawCountQueryProperties().setValue(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid value of JdbcRawCountQuery: " + source, e
            );
        }
    }
}
