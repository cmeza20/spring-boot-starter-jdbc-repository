package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawPaginationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcRawPaginationConverter implements Converter<String, JdbcRawPaginationProperties> {

    @Override
    public JdbcRawPaginationProperties convert(@NonNull String source) {
        try {
            return new JdbcRawPaginationProperties().setValue(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid value of JdbcRawPagination: " + source, e
            );
        }
    }
}
