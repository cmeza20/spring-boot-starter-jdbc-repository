package com.cmeza.spring.jdbc.repository.naming;

public class NoOpNamingStrategy implements NamingStrategy {
    @Override
    public String parse(String origin) {
        return origin;
    }
}
