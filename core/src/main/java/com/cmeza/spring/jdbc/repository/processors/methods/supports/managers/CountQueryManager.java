package com.cmeza.spring.jdbc.repository.processors.methods.supports.managers;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleAnnotationMetadata;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcCountQueryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.CountQueryAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcCountQuery;

import java.util.Map;
import java.util.Objects;

public class CountQueryManager {
    private final JdbcPropertyResolver propertiesResolver;
    private final JdbcCountQueryProperties jdbcCountQueryProperties;

    public CountQueryManager(JdbcPropertyResolver propertiesResolver, JdbcCountQueryProperties jdbcCountQueryProperties) {
        this.propertiesResolver = propertiesResolver;
        this.jdbcCountQueryProperties = jdbcCountQueryProperties;
    }

    public void process(ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        if (Objects.nonNull(jdbcCountQueryProperties)) {
            Map<String, Object> transform = jdbcCountQueryProperties.transform();
            AnnotationMetadata<JdbcCountQuery> countAnnotation = new SimpleAnnotationMetadata<>(JdbcUtils.createAnnotation(JdbcCountQuery.class, transform), 0);

            CountQueryAnnotatedMethodProcessor countQueryAnnotatedMethodProcessor = new CountQueryAnnotatedMethodProcessor();
            countQueryAnnotatedMethodProcessor.setPropertiesResolver(propertiesResolver);
            countQueryAnnotatedMethodProcessor.process(countAnnotation, classMetadata, methodMetadata);
        }
    }
}
