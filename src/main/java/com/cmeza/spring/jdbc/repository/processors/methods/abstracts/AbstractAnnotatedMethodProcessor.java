package com.cmeza.spring.jdbc.repository.processors.methods.abstracts;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.ioc.handler.utils.IocUtil;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.aware.JdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractAnnotatedMethodProcessor<A extends Annotation> extends AbstractNamingStrategy implements AnnotatedMethodProcessor<A>, JdbcRepositoryAware {
    protected static final String LOGGEABLE = "loggeable";
    protected static final String MAPPER = "mapper";
    protected static final String SCHEMA = "schema";
    protected static final String CATALOG = "catalog";
    protected static final String VALUE = "value";
    protected static final String NAME = "name";
    protected JdbcPropertyResolver propertiesResolver;

    protected abstract void annotationProcess(JdbcRepository jdbcRepository, A annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata, Map<String, Object> annotationValues);

    protected abstract JdbcExecutor executorProcess(JdbcRepository jdbcRepository, A annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata);

    @Override
    public A process(AnnotationMetadata<A> annotationMetadata, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        Assert.isTrue(!methodMetadata.hasAttribute(JdbcContractFunctions.METHOD_EXECUTOR), "Only one annotation per method is allowed.");
        A annotation = annotationMetadata.getAnnotation();
        JdbcRepository jdbcRepository = classMetadata.getProcessorResult(JdbcRepository.class);

        //Annotation Process
        Map<String, Object> mapValues = this.bindAnnotation(jdbcRepository, annotation, methodMetadata);
        this.annotationProcess(jdbcRepository, annotation, classMetadata, methodMetadata, mapValues);
        annotation = JdbcUtils.updateAnnotation(annotation, mapValues);

        //Executor Process
        JdbcExecutor executor = this.executorProcess(jdbcRepository, annotation, classMetadata, methodMetadata);
        this.bindExecutor(executor, methodMetadata);

        //Mapper Process
        this.bindMapper(mapValues, methodMetadata);

        return annotation;
    }

    private Map<String, Object> bindAnnotation(JdbcRepository jdbcRepository, A annotation, MethodMetadata methodMetadata) {
        Map<String, Object> mapValues = AnnotationUtils.getAnnotationAttributes(annotation);
        boolean loggeable = jdbcRepository.loggeable();
        if (mapValues.containsKey(LOGGEABLE)) {
            boolean repositoryLoggeable = (boolean) mapValues.get(LOGGEABLE);
            if (repositoryLoggeable) {
                loggeable = repositoryLoggeable;
            }
        }
        mapValues.put(LOGGEABLE, loggeable);
        methodMetadata.addAttribute(JdbcContractFunctions.METHOD_LOGGEABLE, loggeable);
        return mapValues;
    }

    private void bindExecutor(JdbcExecutor executor, MethodMetadata methodMetadata) {
        if (Objects.nonNull(executor)) {
            methodMetadata.addAttribute(JdbcContractFunctions.METHOD_EXECUTOR, executor);
        }
    }

    @SuppressWarnings("unchecked")
    private void bindMapper(Map<String, Object> mapValues, MethodMetadata methodMetadata) {
        boolean needRowMapper = mapValues.containsKey(MAPPER);
        methodMetadata.addAttribute(JdbcContractFunctions.METHOD_NEED_ROWMAPPER, needRowMapper);
        if (needRowMapper) {
            Class<? extends RowMapper<?>> mapper = (Class<? extends RowMapper<?>>) mapValues.get(MAPPER);
            if (!mapper.isInterface() && !Modifier.isAbstract(mapper.getModifiers())) {
                methodMetadata.addAttribute(JdbcContractFunctions.METHOD_ROWMAPPER, mapper);
            }
        }
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
