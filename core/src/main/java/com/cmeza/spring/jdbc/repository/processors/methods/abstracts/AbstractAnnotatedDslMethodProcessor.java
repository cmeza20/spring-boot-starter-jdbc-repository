package com.cmeza.spring.jdbc.repository.processors.methods.abstracts;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleAnnotationMetadata;
import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.aware.JdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;

import java.lang.annotation.Annotation;

public abstract class AbstractAnnotatedDslMethodProcessor<A extends Annotation, B extends Annotation, D extends AbstractProperties> implements AnnotatedMethodProcessor<A>, JdbcRepositoryAware {

    private final AbstractAnnotatedMethodProcessor<B, D> abstractAnnotatedMethodProcessor;
    protected JdbcPropertyResolver jdbcPropertyResolver;

    protected AbstractAnnotatedDslMethodProcessor(AbstractAnnotatedMethodProcessor<B, D> abstractAnnotatedMethodProcessor) {
        this.abstractAnnotatedMethodProcessor = abstractAnnotatedMethodProcessor;
    }

    protected abstract B generateEmptyAnnotation();

    protected abstract Class<A> dslAnnotationType();

    @Override
    public A process(AnnotationMetadata<A> annotationMetadata, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        AnnotationMetadata<B> annotationMetadataB = new SimpleAnnotationMetadata<>(generateEmptyAnnotation(), 0);
        abstractAnnotatedMethodProcessor.setWithParser(false);
        abstractAnnotatedMethodProcessor.setPropertiesResolver(jdbcPropertyResolver);
        abstractAnnotatedMethodProcessor.process(annotationMetadataB, classMetadata, methodMetadata);
        return annotationMetadata.getAnnotation();
    }

    @Override
    public Class<A> getAnnotationType() {
        return dslAnnotationType();
    }

    @Override
    public void setPropertiesResolver(JdbcPropertyResolver propertiesResolver) {
        this.jdbcPropertyResolver = propertiesResolver;
    }

    @Override
    public void setJdbcRepositoryTemplate(JdbcRepositoryTemplate jdbcRepositoryTemplate) {

    }

    @Override
    public void setNamingStrategy(NamingStrategy namingStrategy) {

    }

    @Override
    public boolean findQualified() {
        return false;
    }
}
