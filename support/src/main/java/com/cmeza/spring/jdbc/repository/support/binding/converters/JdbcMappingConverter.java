package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcMappingProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcMappingConverter implements Converter<String, JdbcMappingProperties> {

    @Override
    public JdbcMappingProperties convert(@NonNull String source) {
        try {
            return new JdbcMappingProperties().setTo(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid 'to' of JdbcMapping: " + source, e
            );
        }
    }
}
