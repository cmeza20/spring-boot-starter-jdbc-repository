package com.cmeza.spring.jdbc.repository.contracts;

import com.cmeza.spring.ioc.handler.contracts.IocContract;
import com.cmeza.spring.ioc.handler.contracts.consumers.enums.ConsumerLocation;
import com.cmeza.spring.ioc.handler.contracts.consumers.manager.ConsumerManager;
import com.cmeza.spring.ioc.handler.handlers.IocTarget;
import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.processors.AnnotatedClassProcessor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedParameterProcessor;
import com.cmeza.spring.ioc.handler.processors.IocProcessors;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JdbcContract extends JdbcContractFunctions implements IocContract<JdbcRepository> {

    private final List<AnnotatedClassProcessor<?>> classProcessors;
    private final List<AnnotatedMethodProcessor<?>> methodProcessors;
    private final List<AnnotatedParameterProcessor<?>> parameterProcessors;
    private final JdbcRepositoryProperties jdbcRepositoryProperties;

    @Override
    public void configure(ConsumerManager consumerManager) {
        consumerManager
                .addClassConsumer(ConsumerLocation.AFTER_ANNOTATION_PROCESSOR, afterAnnotationClassProcessor)
                .addMethodConsumer(ConsumerLocation.ON_START, onStartMethodConsumer)
                .addMethodConsumer(ConsumerLocation.AFTER_ANNOTATION_PROCESSOR, afterAnnotationMethodProcessor)
                .addParameterConsumer(ConsumerLocation.ON_END, onEndParameterConsumer)
                .addMethodConsumer(ConsumerLocation.ON_END, onEndMethodConsumer);
    }

    @Override
    public Object execute(ClassMetadata classMetadata, MethodMetadata methodMetadata, Object[] arguments, IocTarget<?> iocTarget) {
        return methodMetadata.getAttribute(METHOD_EXECUTOR, JdbcExecutor.class).execute(methodMetadata, arguments);
    }

    @Override
    public void processors(IocProcessors processors) {
        processors.clearAnnotatedClassProcessors();
        processors.clearAnnotatedMethodProcessors();
        processors.clearAnnotatedParameterProcessors();
        processors.setAnnotatedClassProcessors(classProcessors);
        processors.setAnnotatedMethodProcessors(methodProcessors);
        processors.setAnnotatedParameterProcessors(parameterProcessors);
    }

    @Override
    public boolean onlyDeclaredMethods() {
        return jdbcRepositoryProperties.isOnlyDeclaredMethods();
    }

    @Override
    public boolean onlyMethodDeclaredAnnotations() {
        return jdbcRepositoryProperties.isOnlyMethodDeclaredAnnotations();
    }

    @Override
    public boolean onlyParameterDeclaredAnnotations() {
        return jdbcRepositoryProperties.isOnlyParameterDeclaredAnnotations();
    }
}
