package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.groups.JdbcMappings;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.definitions.MappingDefinition;
import com.cmeza.spring.jdbc.repository.repositories.mappers.MappingDefinitionMapper;

import java.util.Map;

public class MappingsAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcMappings> {

    private final MappingDefinitionMapper mappingDefinitionMapper = new MappingDefinitionMapper();

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcMappings annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        //annotationProcess
    }

    @Override
    protected Object bindDefinition(JdbcMappings annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        MappingDefinition[] mapings = new MappingDefinition[methodMetadata.getMethod().getAnnotationsByType(JdbcMapping.class).length];

        int position = 0;
        for (JdbcMapping mappingAnnotation : annotation.value()) {
            mapings[position] = mappingDefinitionMapper.parseFromMappingAnnotation(mappingAnnotation, position);
            position++;
        }
        return mapings;
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_PARAM_MAPPINGS;
    }

}
