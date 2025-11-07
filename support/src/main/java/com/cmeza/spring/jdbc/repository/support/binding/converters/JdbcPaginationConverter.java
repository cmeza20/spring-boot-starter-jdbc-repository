package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcPaginationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcPaginationConverter implements Converter<String, JdbcPaginationProperties> {

    @Override
    public JdbcPaginationProperties convert(@NonNull String source) {
        try {
            return new JdbcPaginationProperties().setTable(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid table of JdbcPagination: " + source, e
            );
        }
    }
}
