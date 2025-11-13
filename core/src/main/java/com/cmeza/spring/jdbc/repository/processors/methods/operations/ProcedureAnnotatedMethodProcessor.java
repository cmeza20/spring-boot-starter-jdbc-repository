package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.ioc.handler.metadata.ClassMetadata;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.dsl.properties.DslProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.executors.definition.JdbcProcedureExecutor;
import com.cmeza.spring.jdbc.repository.utils.JdbcConstants;
import com.cmeza.spring.jdbc.repository.utils.JdbcMessageUtils;
import com.cmeza.spring.jdbc.repository.utils.JdbcNamedParameterUtils;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.parsers.methods.JdbcProcedureParser;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcProcedureProperties;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProcedureAnnotatedMethodProcessor extends AbstractAnnotatedMethodProcessor<JdbcProcedure, JdbcProcedureProperties> {

    public ProcedureAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(jdbcRepositoryProperties);
    }

    @Override
    protected JdbcExecutor executorProcess(JdbcRepository jdbcRepository, JdbcProcedure annotation, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        return new JdbcProcedureExecutor(annotation);
    }

    @Override
    protected JdbcProcedureProperties dslLocator(JdbcProcedure annotation, DslProperties dslProperties, ClassMetadata classMetadata, MethodMetadata methodMetadata) {
        JdbcProcedureProperties jdbcProcedureProperties = dslProperties.getProcedure();
        if (Objects.isNull(jdbcProcedureProperties)) {
            jdbcProcedureProperties = new JdbcProcedureProperties();
        }

        return jdbcProcedureProperties;
    }

    @Override
    protected void dslParser(JdbcProcedure annotation, JdbcProcedureProperties dsl) {
        PARSER.getParser(JdbcProcedureParser.class).parse(annotation, dsl);
    }

    @Override
    protected void resolvePlaceholders(JdbcProcedureProperties dslProperty, JdbcRepository jdbcRepository, MethodMetadata methodMetadata) {
        dslProperty.setName(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getName(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcProcedure.class, JdbcConstants.NAME)));

        if (dslProperty.getSchema().isEmpty()) {
            dslProperty.setSchema(jdbcRepository.schema());
        } else {
            dslProperty.setSchema(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getSchema(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcProcedure.class, JdbcConstants.SCHEMA)));
        }
        if (dslProperty.getCatalog().isEmpty()) {
            dslProperty.setCatalog(jdbcRepository.catalog());
        } else {
            dslProperty.setCatalog(propertiesResolver.resolveRequiredPlaceholders(dslProperty.getCatalog(), JdbcMessageUtils.validateAnnotationMessage(methodMetadata, JdbcProcedure.class, JdbcConstants.CATALOG)));
        }

        NamingStrategy namingStrategy = this.extractNamingStrategy(dslProperty.getParametersNamingStrategy());
        if (!namingStrategy.getClass().isAssignableFrom(NoOpNamingStrategy.class)) {
            dslProperty.setInParameterNames(this.executeNamingStrategy(dslProperty.getInParameterNames(), namingStrategy));
            dslProperty.setRowMapperParameterName(namingStrategy.parse(dslProperty.getRowMapperParameterName()));
            dslProperty.setOutParameters(JdbcNamedParameterUtils.extractOutParameters(dslProperty.getOutParameters(), namingStrategy));
        }
    }

    @Override
    protected void updateValues(Map<String, Object> values, JdbcProcedureProperties dslProperty) {
        List<Map<String, Object>> parameterList = (List<Map<String, Object>>) values.get("outParameters");

        Parameter[] parameters = Objects.nonNull(parameterList) ? new Parameter[parameterList.size()] : new Parameter[0];
        if (Objects.nonNull(parameterList)) {
            int pos = 0;
            for (Map<String, Object> map : parameterList) {
                parameters[pos] = JdbcUtils.createAnnotation(Parameter.class, map);
                pos++;
            }
        }

        values.put("outParameters", parameters);
    }

}
