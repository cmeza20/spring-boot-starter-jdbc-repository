package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.managers.RawCountQueryManager;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcPaginationExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawPagination;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcRawPaginationParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawPaginationProperties;

import java.util.Map;
import java.util.Objects;

public class RawPaginationAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcRawPagination, JdbcRawPaginationProperties> {
    public RawPaginationAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcRawPagination annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcPaginationExecutor(annotation);
    }

    @Override
    protected JdbcRawPaginationProperties dslLocator(JdbcRawPagination annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcRawPaginationProperties jdbcRawPaginationProperties = dslProperties.getRawPagination();
        if (Objects.isNull(jdbcRawPaginationProperties)) {
            jdbcRawPaginationProperties = new JdbcRawPaginationProperties();
        }

        //Count query
        RawCountQueryManager rawCountQueryManager = new RawCountQueryManager(propertiesResolver, jdbcRawPaginationProperties.getRawCountQuery());
        rawCountQueryManager.process(classMetadata, methodMetadata);

        return jdbcRawPaginationProperties;
    }

    @Override
    protected void dslParser(JdbcRawPagination annotation, JdbcRawPaginationProperties dsl) {
        PARSER.getParser(JdbcRawPaginationParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcRawPaginationProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setValue(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getValue(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcRawPagination.class, JdbcConstants.VALUE)));
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcRawPaginationProperties dslProperty) {
        //updateValues
    }

}
