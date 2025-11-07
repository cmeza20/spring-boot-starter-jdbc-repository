package com.cmeza.spring.jdbc.repository.support.parsers.supports;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcRawCountQuery;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcRawCountQueryProperties;
import org.apache.commons.lang3.StringUtils;

public class JdbcRawCountQueryParser implements IParser<JdbcRawCountQuery, JdbcRawCountQueryProperties> {
    @Override
    public void parse(JdbcRawCountQuery annotation, JdbcRawCountQueryProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.value())) {
            dslProperty.setValue(annotation.value());
        }
    }
}
