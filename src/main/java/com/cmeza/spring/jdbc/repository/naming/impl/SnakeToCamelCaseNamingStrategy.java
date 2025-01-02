package com.cmeza.spring.jdbc.repository.naming.impl;

import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import org.apache.commons.text.CaseUtils;

public class SnakeToCamelCaseNamingStrategy implements NamingStrategy {
    @Override
    public String parse(String origin) {
        return CaseUtils.toCamelCase(origin, false, '_');
    }
}
