package com.cmeza.spring.jdbc.repository.processors.methods.supports.managers;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleAnnotationMetadata;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.MappingsAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.groups.JdbcMappings;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcMappingProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MappingsManager {
    private final JdbcPropertyResolver propertiesResolver;
    private final List<JdbcMappingProperties> jdbcMappingProperties;

    public MappingsManager(JdbcPropertyResolver propertiesResolver, List<JdbcMappingProperties> jdbcMappingProperties) {
        this.propertiesResolver = propertiesResolver;
        this.jdbcMappingProperties = jdbcMappingProperties;
    }

    public void process(ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        if (Objects.nonNull(jdbcMappingProperties) && !jdbcMappingProperties.isEmpty()) {
            Map<String, Object> mappingsAnnotationMap = new HashMap<>();
            JdbcMapping[] mappingsAnnotationArray = new JdbcMapping[jdbcMappingProperties.size()];

            int i = 0;
            for (JdbcMappingProperties jdbcMappingProperty : jdbcMappingProperties) {
                mappingsAnnotationArray[i] = JdbcUtils.createAnnotation(JdbcMapping.class, jdbcMappingProperty.transform());
                i++;
            }

            mappingsAnnotationMap.put("value", mappingsAnnotationArray);

            AnnotationMetadata<JdbcMappings> mappingsAnnotation = new SimpleAnnotationMetadata<>(JdbcUtils.createAnnotation(JdbcMappings.class, mappingsAnnotationMap), 0);

            MappingsAnnotatedMethodProcessor mappingsAnnotatedMethodProcessor = new MappingsAnnotatedMethodProcessor();
            mappingsAnnotatedMethodProcessor.setPropertiesResolver(propertiesResolver);
            mappingsAnnotatedMethodProcessor.setAllowDuplicate(true);
            mappingsAnnotatedMethodProcessor.process(mappingsAnnotation, classMetadata, methodMetadata);
        }
    }
}
