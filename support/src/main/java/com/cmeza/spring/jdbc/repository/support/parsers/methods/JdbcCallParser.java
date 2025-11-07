package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcCallProperties;
import org.apache.commons.lang3.StringUtils;

public class JdbcCallParser implements IParser<JdbcCall, JdbcCallProperties> {

    @Override
    public void parse(JdbcCall annotation, JdbcCallProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.value())) {
            dslProperty.setValue(annotation.value());
        }
        if (StringUtils.isNotEmpty(annotation.schema())) {
            dslProperty.setSchema(annotation.schema());
        }
        if (StringUtils.isNotEmpty(annotation.catalog())) {
            dslProperty.setCatalog(annotation.catalog());
        }
        if (annotation.parameters().length > 0) {
            dslProperty.setParameters(annotation.parameters());
        }
        if (!annotation.type().equals(JdbcCall.CallType.CALL)) {
            dslProperty.setType(annotation.type());
        }
        if (annotation.loggable()) {
            dslProperty.setLoggable(true);
        }
        if (!annotation.parametersNamingStrategy().equals(NoOpNamingStrategy.class)) {
            dslProperty.setParametersNamingStrategy(annotation.parametersNamingStrategy());
        }
    }
}
