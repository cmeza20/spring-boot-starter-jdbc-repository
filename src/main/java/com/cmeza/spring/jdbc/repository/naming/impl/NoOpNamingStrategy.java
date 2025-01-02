package com.cmeza.spring.jdbc.repository.naming.impl;

import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;

public class NoOpNamingStrategy implements NamingStrategy {
    @Override
    public String parse(String origin) {
        return origin;
    }
}
