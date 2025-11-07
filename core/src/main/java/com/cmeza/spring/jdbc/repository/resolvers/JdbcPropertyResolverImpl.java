package com.cmeza.spring.jdbc.repository.resolvers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

@RequiredArgsConstructor
public class JdbcPropertyResolverImpl implements JdbcPropertyResolver {

    private final Environment environment;

    @Override
    public String resolvePlaceholders(String key) {
        return this.resolvePlaceholders(key, null);
    }

    @Override
    public String resolvePlaceholders(String key, String message) {
        String cleanKey = cleanKey(key, message);
        return environment.resolvePlaceholders(cleanKey);
    }

    @Override
    public String resolveRequiredPlaceholders(String key) {
        return this.resolveRequiredPlaceholders(key, null);
    }

    @Override
    public String resolveRequiredPlaceholders(String key, String message) {
        String cleanKey = cleanKey(key, message);
        return environment.resolveRequiredPlaceholders(cleanKey);
    }

    private String cleanKey(String key, String customMessage) {
        Assert.notNull(key, StringUtils.isEmpty(customMessage) ? "Property key required!" : customMessage);
        String cleanKey;
        if (key.toLowerCase().startsWith("file:/")) {
            cleanKey = key.replace("file:/", "");
            cleanKey = "${" + FilenameUtils.getName(cleanKey) + "}";
        } else {
            cleanKey = key;
        }
        return cleanKey;
    }
}
