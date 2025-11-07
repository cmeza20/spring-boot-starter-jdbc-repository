package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcExecuteProperties;
import org.apache.commons.lang3.StringUtils;

public class JdbcExecuteParser implements IParser<JdbcExecute, JdbcExecuteProperties> {
    @Override
    public void parse(JdbcExecute annotation, JdbcExecuteProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.value())) {
            dslProperty.setValue(annotation.value());
        }
        if (annotation.loggable()) {
             dslProperty.setLoggable(true);
        }
    }
}
