package com.cmeza.spring.jdbc.repository.resolvers;

public interface JdbcPropertyResolver {
    String resolvePlaceholders(String key);

    String resolvePlaceholders(String key, String message);

    String resolveRequiredPlaceholders(String key);

    String resolveRequiredPlaceholders(String key, String message);
}
