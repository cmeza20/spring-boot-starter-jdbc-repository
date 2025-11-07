package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.groups.JdbcMappings;
import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;
import com.cmeza.spring.jdbc.repository.support.parsers.supports.JdbcMappingParser;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Map;

public class MappingsAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcMappings> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcMappings annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        //annotationProcess
    }

    @Override
    protected Object bindDefinition(JdbcMappings annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        MappingDefinition[] mappings = methodMetadata.getAttribute(tagUniqueSupport(), MappingDefinition[].class, new MappingDefinition[0]);

        if (annotation.value().length > 0) {

            MappingDefinition[] currentMappings = new MappingDefinition[annotation.value().length];
            int position = 0;
            for (JdbcMapping mappingAnnotation : annotation.value()) {
                currentMappings[position] = PARSER.getParser(JdbcMappingParser.class).parseFromMappingAnnotation(mappingAnnotation, position);
                position++;
            }

            mappings = ArrayUtils.addAll(mappings, currentMappings);
        }


        return mappings;
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_PARAM_MAPPINGS;
    }

}
