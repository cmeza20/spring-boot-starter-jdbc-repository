package com.cmeza.spring.jdbc.repository.processors.methods.supports.managers;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleAnnotationMetadata;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.ParamFilterAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcParamFilter;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ParamFilterManager {
    private final JdbcPropertyResolver propertiesResolver;
    private final String[] paramFilters;

    public ParamFilterManager(JdbcPropertyResolver propertiesResolver, String[] paramFilters) {
        this.propertiesResolver = propertiesResolver;
        this.paramFilters = paramFilters;
    }

    public void process(ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        if (Objects.nonNull(paramFilters)) {
            Map<String, Object> transform = new HashMap<>();
            transform.put("value", paramFilters);

            AnnotationMetadata<JdbcParamFilter> paramFilterAnnotation = new SimpleAnnotationMetadata<>(JdbcUtils.createAnnotation(JdbcParamFilter.class, transform), 0);

            ParamFilterAnnotatedMethodProcessor paramFilterAnnotatedMethodProcessor = new ParamFilterAnnotatedMethodProcessor();
            paramFilterAnnotatedMethodProcessor.setPropertiesResolver(propertiesResolver);
            paramFilterAnnotatedMethodProcessor.process(paramFilterAnnotation, classMetadata, methodMetadata);
        }
    }
}
