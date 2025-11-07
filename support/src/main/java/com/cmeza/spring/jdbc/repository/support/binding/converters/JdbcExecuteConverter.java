package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcExecuteProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcExecuteConverter implements Converter<String, JdbcExecuteProperties> {

    @Override
    public JdbcExecuteProperties convert(@NonNull String source) {
        try {
            return new JdbcExecuteProperties().setValue(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid value of JdbcExecute: " + source, e
            );
        }
    }
}
