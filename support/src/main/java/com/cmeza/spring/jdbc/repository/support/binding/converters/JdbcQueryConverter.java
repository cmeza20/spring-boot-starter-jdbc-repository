package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcQueryProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class JdbcQueryConverter implements Converter<String, JdbcQueryProperties> {

    @Override
    public JdbcQueryProperties convert(@NonNull String source) {
        try {
            return new JdbcQueryProperties().setTable(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid table of JdbcQuery: " + source, e
            );
        }
    }
}
