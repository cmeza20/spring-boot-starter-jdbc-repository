package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcProcedureProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcProcedureConverter implements Converter<String, JdbcProcedureProperties> {

    @Override
    public JdbcProcedureProperties convert(@NonNull String source) {
        try {
            return new JdbcProcedureProperties().setName(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid name of JdbcProcedure: " + source, e
            );
        }
    }
}
