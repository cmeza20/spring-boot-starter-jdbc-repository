package com.cmeza.spring.jdbc.repository.processors.methods.supports;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractSupportMethodProcessor;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcParamFilter;
import com.cmeza.spring.jdbc.repository.support.definitions.ParamFilterDefinition;

import java.util.Map;

public class ParamFilterAnnotatedMethodProcessor extends AbstractSupportMethodProcessor<JdbcParamFilter> {

    @Override
    protected void annotationProcess(JdbcRepository jdbcRepository, JdbcParamFilter annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues) {
        //annotationProcess
    }

    @Override
    protected Object bindDefinition(JdbcParamFilter annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return ParamFilterDefinition.builder().values(annotation.value()).build();
    }

    @Override
    protected String tagUniqueSupport() {
        return JdbcContractFunctions.METHOD_PARAM_FILTERS;
    }
}
