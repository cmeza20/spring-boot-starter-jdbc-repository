package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.support.parsers.supports.JdbcMappingParser;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;

import java.util.Map;

public class MappingAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcMapping> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcMapping annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        //annotationProcess
    }

    @Override
    protected Object bindDefinition(JdbcMapping annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new MappingDefinition[]{
                PARSER.getParser(JdbcMappingParser.class).parseFromMappingAnnotation(annotation, 0)
        };
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_PARAM_MAPPINGS;
    }
}
