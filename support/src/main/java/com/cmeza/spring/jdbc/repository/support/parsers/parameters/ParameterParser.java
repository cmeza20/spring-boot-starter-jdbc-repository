package com.cmeza.spring.jdbc.repository.support.parsers.parameters;

import com.cmeza.spring.jdbc.repository.support.binding.SqlType;
import com.cmeza.spring.jdbc.repository.support.parsers.IParser;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.support.properties.parameters.ParameterProperties;
import org.apache.commons.lang3.StringUtils;

public class ParameterParser implements IParser<Parameter, ParameterProperties> {
    @Override
    public void parse(Parameter annotation, ParameterProperties dslProperty) {
        if (StringUtils.isNotEmpty(annotation.value())) {
            dslProperty.setValue(annotation.value());
        }
        if (annotation.type() != 0) {
            dslProperty.setType(new SqlType(annotation.type()));
        }
        if (annotation.order() != 0) {
            dslProperty.setOrder(annotation.order());
        }
    }
}
