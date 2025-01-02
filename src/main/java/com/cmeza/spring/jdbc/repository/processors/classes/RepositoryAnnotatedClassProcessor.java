package com.cmeza.spring.jdbc.repository.processors.classes;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.processors.AnnotatedClassProcessor;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.aware.JdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;

public class RepositoryAnnotatedClassProcessor implements AnnotatedClassProcessor<JdbcRepository>, JdbcRepositoryAware {
    private JdbcPropertyResolver propertiesResolver;

    @Override
    public JdbcRepository process(AnnotationMetadata<JdbcRepository> annotationMetadata, ClassMetadata classMetadata) {
        JdbcRepository annotation = annotationMetadata.getAnnotation();
        Map<String, Object> mapValues = AnnotationUtils.getAnnotationAttributes(annotation);
        mapValues.put("catalog", propertiesResolver.resolveRequiredPlaceholders(annotation.catalog()));
        mapValues.put("schema", propertiesResolver.resolveRequiredPlaceholders(annotation.schema()));
        return JdbcUtils.updateAnnotation(annotation, mapValues);
    }

    @Override
    public void setPropertiesResolver(JdbcPropertyResolver propertiesResolver) {
        this.propertiesResolver = propertiesResolver;
    }

    @Override
    public void setJdbcRepositoryTemplate(JdbcRepositoryTemplate jdbcRepositoryTemplate) {
        //Override empty
    }

    @Override
    public void setNamingStrategy(NamingStrategy namingStrategy) {
        //Override empty
    }

    @Override
    public boolean findQualified() {
        return false;
    }
}
