package com.cmeza.spring.jdbc.repository.processors.methods.abstracts;

import com.cmeza.spring.ioc.handler.metadata.AnnotationMetadata;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.ioc.handler.utils.IocUtil;
import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContractFunctions;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.managers.MappingsManager;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.parsers.Parser;
import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcMappingProperties;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractAnnotatedMethodProcessor<A extends Annotation, D extends AbstractProperties> extends AbstractNamingStrategy implements AnnotatedMethodProcessor<A> {
    protected static final String LOGGABLE = "loggable";
    protected static final String MAPPER = "mapper";
    protected static final String SCHEMA = "schema";
    protected static final Parser PARSER = Parser.getInstance();
    protected final JdbcRepositoryProperties jdbcRepositoryProperties;
    protected JdbcPropertyResolver propertiesResolver;
    @Setter
    private boolean withParser = true;

    protected AbstractAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        this.jdbcRepositoryProperties = jdbcRepositoryProperties;
    }

    protected abstract JdbcExecutor executorProcess(JdbcRepository jdbcRepository, A annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata);

    protected abstract D dslLocator(A annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata);

    protected abstract void dslParser(A annotation, D dsl);

    protected abstract void resolvePlaceholders(D dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata);

    protected abstract void updateValues(Map<String, Object> values, D dslProperty);

    @Override
    public A process(AnnotationMetadata<A> annotationMetadata, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        Assert.isTrue(!methodMetadata.hasAttribute(JdbcContractFunctions.METHOD_EXECUTOR), "Only one annotation per method is allowed.");
        JdbcRepository jdbcRepository = classMetadata.getProcessorResult(JdbcRepository.class);

        A annotation = annotationMetadata.getAnnotation();

        //Dsl properties
        DslProperties dslProperties = this.bindDslproperties(jdbcRepository, classMetadata, methodMetadata);

        //Dsl
        D dslProperty = this.dslLocator(annotation, dslProperties, classMetadata, methodMetadata);
        if (withParser) {
            this.dslParser(annotation, dslProperty);
        }


        //Mappings
        List<JdbcMappingProperties> mappings = dslProperty.getMappings();
        MappingsManager mappingsManager = new MappingsManager(propertiesResolver, mappings);
        mappingsManager.process(classMetadata, methodMetadata);

        //Resolve placeholders
        this.resolvePlaceholders(dslProperty, jdbcRepository, methodMetadata);

        //Map Values
        Map<String, Object> mapValues = this.bindMapValues(jdbcRepository, dslProperty, methodMetadata);

        //Update Values
        this.updateValues(mapValues, dslProperty);

        //update annotation
        annotation = JdbcUtils.updateAnnotation(annotation, mapValues);

        //Executor Process
        JdbcExecutor executor = this.executorProcess(jdbcRepository, annotation, classMetadata, methodMetadata);
        this.bindExecutor(executor, methodMetadata);

        //Mapper Process
        this.bindMapper(mapValues, methodMetadata);

        return annotation;
    }

    private Map<String, Object> bindMapValues(JdbcRepository jdbcRepository, D dslProperty, MethodMetadata methodMetadata) {
        boolean loggable = jdbcRepository.loggable();
        Map<String, Object> mapValues = dslProperty.transform();
        if (mapValues.containsKey(LOGGABLE)) {
            boolean repositoryLoggable = (boolean) mapValues.get(LOGGABLE);
            if (repositoryLoggable) {
                loggable = repositoryLoggable;
            }
        }
        mapValues.put(LOGGABLE, loggable);
        methodMetadata.addAttribute(JdbcContractFunctions.METHOD_LOGGABLE, loggable);
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

    private DslProperties bindDslproperties(JdbcRepository jdbcRepository, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        String dslClassName = classMetadata.getTargetClass().getSimpleName();
        if (StringUtils.isNotEmpty(jdbcRepository.dslName())) {
            dslClassName = jdbcRepository.dslName();
        }

        DslProperties dslProperties = jdbcRepositoryProperties.findDsl(dslClassName, methodMetadata.getMethod().getName());
        if (Objects.isNull(dslProperties)) {
            return new DslProperties();
        }
        return dslProperties;
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
