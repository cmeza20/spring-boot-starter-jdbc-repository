package com.cmeza.spring.jdbc.repository.support.parsers;

import com.cmeza.spring.jdbc.repository.support.exceptions.ParserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class Parser {
    private static volatile Parser instance;
    private final Map<String, Object> parsers = new HashMap<>();

    private Parser() {
    }

    public static Parser getInstance() {
        Parser result = instance;
        if (result != null) {
            return result;
        }

        synchronized (Parser.class) {
            if (instance == null) {
                instance = new Parser();
            }
            return instance;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getParser(Class<T> clazz) {
        initialize();
        return (T) Optional.ofNullable(parsers.get(clazz.getName())).orElseThrow(() -> new ParserException("Parser " + clazz + " not found"));
    }

    private void initialize() {
        if (parsers.isEmpty()) {
            BeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
            ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
            TypeFilter typeFilter = new AssignableTypeFilter(IParser.class);
            scanner.addIncludeFilter(typeFilter);
            scanner.findCandidateComponents("com.cmeza.spring.jdbc.repository.support.parsers").forEach(beanDefinition -> {
                if (beanDefinition instanceof ScannedGenericBeanDefinition) {
                    ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) beanDefinition;
                    try {
                        String className = scannedGenericBeanDefinition.getBeanClassName();
                        Class<?> beanClass = Class.forName(className);
                        Object bean = BeanUtils.instantiateClass(beanClass);

                        parsers.put(className, bean);
                    } catch (Exception ignore) {
                        //ignore
                    }
                }
            });
        }
    }
}
