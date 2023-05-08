package com.cmeza.spring.jdbc.repository.resolvers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

@RequiredArgsConstructor
public class JdbcPropertyResolverImpl implements JdbcPropertyResolver {

    private final Environment environment;

    @Override
    public String resolvePlaceholders(String key) {
        String cleanKey = cleanKey(key);
        return environment.resolvePlaceholders(cleanKey);
    }

    @Override
    public String resolveRequiredPlaceholders(String key) {
        String cleanKey = cleanKey(key);
        return environment.resolveRequiredPlaceholders(cleanKey);
    }

    private String cleanKey(String key) {
        Assert.notNull(key, "Property key required!");
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
