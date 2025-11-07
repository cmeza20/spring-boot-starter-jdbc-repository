package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.parsers.Parser;
import com.cmeza.spring.jdbc.repository.support.parsers.parameters.ParameterParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcFunction;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcFunctionProperties;
import com.cmeza.spring.jdbc.repository.support.properties.parameters.ParameterProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public class JdbcFunctionParser implements IParser<JdbcFunction, JdbcFunctionProperties> {
    private static final Parser parser = Parser.getInstance();

    @Override
    public void parse(JdbcFunction annotation, JdbcFunctionProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.name())) {
            dslProperty.setName(annotation.name());
        }
        if (StringUtils.isNotEmpty(annotation.schema())) {
            dslProperty.setSchema(annotation.schema());
        }
        if (StringUtils.isNotEmpty(annotation.catalog())) {
            dslProperty.setCatalog(annotation.catalog());
        }
        if (annotation.inParameterNames().length > 0) {
            dslProperty.setInParameterNames(annotation.inParameterNames());
        }
        if (annotation.outParameters().length > 0) {
            ParameterParser parameterParser = parser.getParser(ParameterParser.class);
            List<ParameterProperties> outParameters = new ArrayList<>();
            for (Parameter parameter : annotation.outParameters()) {
                ParameterProperties parameterProperties = new ParameterProperties();
                parameterParser.parse(parameter, parameterProperties);
                outParameters.add(parameterProperties);
            }
            dslProperty.setOutParameters(outParameters);
        }
        if (!annotation.rowMapperParameterName().equals("result")) {
            dslProperty.setRowMapperParameterName(annotation.rowMapperParameterName());
        }
        if (!annotation.mapper().equals(RowMapper.class)) {
            dslProperty.setMapper(annotation.mapper());
        }
        if (annotation.accessCallParameterMetaData()) {
            dslProperty.setAccessCallParameterMetaData(true);
        }
        if (annotation.loggable()) {
            dslProperty.setLoggable(true);
        }
        if (!annotation.parametersNamingStrategy().equals(NoOpNamingStrategy.class)) {
            dslProperty.setParametersNamingStrategy(annotation.parametersNamingStrategy());
        }

    }
}
