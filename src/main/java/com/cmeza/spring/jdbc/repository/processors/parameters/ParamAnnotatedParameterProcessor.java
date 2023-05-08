package com.cmeza.spring.jdbc.repository.processors.parameters;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.ParameterMetadata;
import com.cmeza.spring.ioc.handler.processors.AnnotatedParameterProcessor;
import com.cmeza.spring.jdbc.repository.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import org.springframework.util.Assert;

public class ParamAnnotatedParameterProcessor implements AnnotatedParameterProcessor<JdbcParam> {

    @Override
    public JdbcParam process(AnnotationMetadata<JdbcParam> annotationMetadata, ClassMetadata classMetadata, MethodMetadata methodMetadata, ParameterMetadata parameterMetadata) {
        Assert.isTrue(!parameterMetadata.hasAttribute(JdbcContractFunctions.PARAMETER_NAME), "Only one annotation per parameter is allowed.");

        JdbcParam annotation = annotationMetadata.getAnnotation();
        parameterMetadata.addAttribute(JdbcContractFunctions.PARAMETER_NAME, annotation.value());
        parameterMetadata.addAttribute(JdbcContractFunctions.PARAMETER_TYPE, annotation.type());
        return annotation;
    }
}
