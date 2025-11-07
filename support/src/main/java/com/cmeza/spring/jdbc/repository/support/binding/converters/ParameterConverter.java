package com.cmeza.spring.jdbc.repository.support.binding.converters;

import com.cmeza.spring.jdbc.repository.support.properties.parameters.ParameterProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

@ConfigurationPropertiesBinding
public class ParameterConverter implements Converter<String, ParameterProperties> {

    @Override
    public ParameterProperties convert(@NonNull String source) {
        try {
            return new ParameterProperties().setValue(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Invalid value of Parameter: " + source, e
            );
        }
    }
}
