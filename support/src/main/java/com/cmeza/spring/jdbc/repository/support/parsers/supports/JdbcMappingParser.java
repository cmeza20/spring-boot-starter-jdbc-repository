package com.cmeza.spring.jdbc.repository.support.parsers.supports;

import com.cmeza.spring.jdbc.repository.support.binding.SqlType;
import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcMappingProperties;
import org.apache.commons.lang3.StringUtils;

public class JdbcMappingParser implements IParser<JdbcMapping, JdbcMappingProperties> {
    @Override
    public void parse(JdbcMapping annotation, JdbcMappingProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.to())) {
            dslProperty.setTo(annotation.to());
        }
        if (StringUtils.isNotEmpty(annotation.from())) {
            dslProperty.setFrom(annotation.from());
        }
        if (annotation.type() != 0) {
            dslProperty.setType(new SqlType(annotation.type()));
        }
    }

    public MappingDefinition parseFromMappingAnnotation(JdbcMapping annotation, int position) {
        return new MappingDefinition()
                .setTo(annotation.to())
                .setFrom(annotation.from())
                .setType(annotation.type())
                .setPosition(position);
    }
}
