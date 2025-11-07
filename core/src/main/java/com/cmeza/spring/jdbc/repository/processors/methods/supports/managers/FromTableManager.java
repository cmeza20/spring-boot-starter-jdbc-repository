package com.cmeza.spring.jdbc.repository.processors.methods.supports.managers;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleAnnotationMetadata;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcFromTableProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.FromTableAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcFromTable;

import java.util.Map;
import java.util.Objects;

public class FromTableManager {
    private final JdbcPropertyResolver propertiesResolver;
    private final JdbcFromTableProperties jdbcFromTableProperties;

    public FromTableManager(JdbcPropertyResolver propertiesResolver, JdbcFromTableProperties jdbcFromTableProperties) {
        this.propertiesResolver = propertiesResolver;
        this.jdbcFromTableProperties = jdbcFromTableProperties;
    }

    public void process(ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        if (Objects.nonNull(jdbcFromTableProperties)) {
            Map<String, Object> transform = jdbcFromTableProperties.transform();
            AnnotationMetadata<JdbcFromTable> fromTableAnnotation = new SimpleAnnotationMetadata<>(JdbcUtils.createAnnotation(JdbcFromTable.class, transform), 0);

            FromTableAnnotatedMethodProcessor fromTableAnnotatedMethodProcessor = new FromTableAnnotatedMethodProcessor();
            fromTableAnnotatedMethodProcessor.setPropertiesResolver(propertiesResolver);
            fromTableAnnotatedMethodProcessor.process(fromTableAnnotation, classMetadata, methodMetadata);
        }
    }
}
