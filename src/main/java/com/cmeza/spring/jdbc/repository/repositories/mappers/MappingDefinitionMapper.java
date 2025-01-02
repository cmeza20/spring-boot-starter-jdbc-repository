package com.cmeza.spring.jdbc.repository.repositories.mappers;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.repositories.definitions.MappingDefinition;

public class MappingDefinitionMapper {

    public MappingDefinition parseFromMappingAnnotation(JdbcMapping annotation, int position) {
        return new MappingDefinition()
                .setTo(annotation.to())
                .setFrom(annotation.from())
                .setType(annotation.type())
                .setPosition(position);
    }

}
