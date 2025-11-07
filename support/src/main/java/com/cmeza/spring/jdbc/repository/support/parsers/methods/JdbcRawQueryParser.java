package com.cmeza.spring.jdbc.repository.support.parsers.methods;

import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawQuery;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawQueryProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;

public class JdbcRawQueryParser implements IParser<JdbcRawQuery, JdbcRawQueryProperties> {
    @Override
    public void parse(JdbcRawQuery annotation, JdbcRawQueryProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.value())) {
            dslProperty.setValue(annotation.value());
        }
        if (!annotation.mapper().equals(RowMapper.class)) {
            dslProperty.setMapper(annotation.mapper());
        }
        if (annotation.loggable()) {
            dslProperty.setLoggable(true);
        }
    }
}
