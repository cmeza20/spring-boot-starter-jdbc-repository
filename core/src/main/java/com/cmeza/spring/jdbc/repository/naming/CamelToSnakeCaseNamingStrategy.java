package com.cmeza.spring.jdbc.repository.naming;

import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;

import java.util.regex.Pattern;

public class CamelToSnakeCaseNamingStrategy implements NamingStrategy {
    private static final String CAMEL_CASE_REGEX = "([a-z]+)([A-Z]+)";
    private static final String SNAKE_CASE_PATTERN = "$1\\_$2";

    @Override
    public String parse(String origin) {
        return Pattern.compile(CAMEL_CASE_REGEX).matcher(origin).replaceAll(SNAKE_CASE_PATTERN).toLowerCase();
    }
}
