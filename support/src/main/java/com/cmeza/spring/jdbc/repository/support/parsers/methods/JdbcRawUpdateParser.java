package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawUpdateProperties;
import org.apache.commons.lang3.StringUtils;

public class JdbcRawUpdateParser implements IParser<JdbcRawUpdate, JdbcRawUpdateProperties> {
    @Override
    public void parse(JdbcRawUpdate annotation, JdbcRawUpdateProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.value())) {
            dslProperty.setValue(annotation.value());
        }
        if (annotation.keyColumnNames().length > 0) {
            dslProperty.setKeyColumnNames(annotation.keyColumnNames());
        }
        if (annotation.loggable()) {
            dslProperty.setLoggable(true);
        }
        if (!annotation.columnsNamingStrategy().equals(NoOpNamingStrategy.class)) {
            dslProperty.setColumnsNamingStrategy(annotation.columnsNamingStrategy());
        }
    }
}
