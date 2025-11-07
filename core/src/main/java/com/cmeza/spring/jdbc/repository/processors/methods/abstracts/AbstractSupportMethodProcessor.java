package com.cmeza.spring.jdbc.repository.processors.methods.abstracts;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.ioc.handler.utils.IocUtil;
import com.cmeza.spring.jdbc.repository.support.parsers.Parser;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractSupportMethodProcessor<A extends Annotation> extends AbstractNamingStrategy implements AnnotatedMethodProcessor<A> {
    protected static final String SCHEMA = "schema";
    protected static final String CATALOG = "catalog";
    protected static final String VALUE = "value";

    @Setter
    @Getter
    private boolean allowDuplicate = false;

    protected static final Parser PARSER = Parser.getInstance();

    protected JdbcPropertyResolver propertiesResolver;

    protected abstract void annotationProcess(JdbcRepository jdbcRepository, A annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues);

    protected abstract Object bindDefinition(A annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata);

    protected abstract String tagUniqueSupport();

    @Override
    public A process(AnnotationMetadata<A> annotationMetadata, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        if (!allowDuplicate) {
            Assert.isTrue(!methodMetadata.hasAttribute(tagUniqueSupport()), "Only one annotation per method is allowed.");
        }

        A annotation = annotationMetadata.getAnnotation();
        JdbcRepository jdbcRepository = classMetadata.getProcessorResult(JdbcRepository.class);

        //Annotation Process
        Map<String, Object> mapValues = AnnotationUtils.getAnnotationAttributes(annotation);
        this.annotationProcess(jdbcRepository, annotation, classMetadata, methodMetadata, mapValues);
        annotation = JdbcUtils.updateAnnotation(annotation, mapValues);

        Object definition = bindDefinition(annotation, classMetadata, methodMetadata);
        if (Objects.nonNull(definition)) {
            methodMetadata.addAttribute(tagUniqueSupport(), definition);
        }

        return annotation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<A> getAnnotationType() {
        return (Class<A>) IocUtil.getGenericSuperClass(this);
    }

    @Override
    public void setPropertiesResolver(JdbcPropertyResolver propertiesResolver) {
        this.propertiesResolver = propertiesResolver;
    }

}
