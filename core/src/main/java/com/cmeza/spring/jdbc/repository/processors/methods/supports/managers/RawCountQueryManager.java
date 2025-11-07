package com.cmeza.spring.jdbc.repository.processors.methods.supports.managers;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleAnnotationMetadata;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcRawCountQueryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.RawCountQueryAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcRawCountQuery;

import java.util.Map;
import java.util.Objects;

public class RawCountQueryManager {
    private final JdbcPropertyResolver propertiesResolver;
    private final JdbcRawCountQueryProperties jdbcRawCountQueryProperties;

    public RawCountQueryManager(JdbcPropertyResolver propertiesResolver, JdbcRawCountQueryProperties jdbcRawCountQueryProperties) {
        this.propertiesResolver = propertiesResolver;
        this.jdbcRawCountQueryProperties = jdbcRawCountQueryProperties;
    }

    public void process(ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        if (Objects.nonNull(jdbcRawCountQueryProperties)) {
            Map<String, Object> transform = jdbcRawCountQueryProperties.transform();
            AnnotationMetadata<JdbcRawCountQuery> countAnnotation = new SimpleAnnotationMetadata<>(JdbcUtils.createAnnotation(JdbcRawCountQuery.class, transform), 0);

            RawCountQueryAnnotatedMethodProcessor countQueryAnnotatedMethodProcessor = new RawCountQueryAnnotatedMethodProcessor();
            countQueryAnnotatedMethodProcessor.setPropertiesResolver(propertiesResolver);
            countQueryAnnotatedMethodProcessor.process(countAnnotation, classMetadata, methodMetadata);
        }
    }
}
