package com.cmeza.spring.jdbc.repository.processors.methods.supports.managers;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleAnnotationMetadata;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcJoinTableProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.JoinTablesAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.groups.JdbcJoinTables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JoinTablesManager {
    private final JdbcPropertyResolver propertiesResolver;
    private final List<JdbcJoinTableProperties> jdbcJoinTableProperties;

    public JoinTablesManager(JdbcPropertyResolver propertiesResolver, List<JdbcJoinTableProperties> jdbcJoinTableProperties) {
        this.propertiesResolver = propertiesResolver;
        this.jdbcJoinTableProperties = jdbcJoinTableProperties;
    }

    public void process(ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        if (Objects.nonNull(jdbcJoinTableProperties) && !jdbcJoinTableProperties.isEmpty()) {
            Map<String, Object> joinTablesAnnotationMap = new HashMap<>();
            JdbcJoinTable[] joinTableAnnotationArray = new JdbcJoinTable[jdbcJoinTableProperties.size()];

            int i = 0;
            for (JdbcJoinTableProperties joinTableProperties : jdbcJoinTableProperties) {
                joinTableAnnotationArray[i] = JdbcUtils.createAnnotation(JdbcJoinTable.class, joinTableProperties.transform());
                i++;
            }

            joinTablesAnnotationMap.put("value", joinTableAnnotationArray);

            AnnotationMetadata<JdbcJoinTables> joinTablesAnnotation = new SimpleAnnotationMetadata<>(JdbcUtils.createAnnotation(JdbcJoinTables.class, joinTablesAnnotationMap), 0);

            JoinTablesAnnotatedMethodProcessor joinTablesAnnotatedMethodProcessor = new JoinTablesAnnotatedMethodProcessor();
            joinTablesAnnotatedMethodProcessor.setPropertiesResolver(propertiesResolver);
            joinTablesAnnotatedMethodProcessor.setAllowDuplicate(true);
            joinTablesAnnotatedMethodProcessor.process(joinTablesAnnotation, classMetadata, methodMetadata);
        }
    }
}
