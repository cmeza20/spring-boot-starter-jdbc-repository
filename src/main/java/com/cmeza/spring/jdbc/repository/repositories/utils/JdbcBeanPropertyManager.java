package com.cmeza.spring.jdbc.repository.repositories.utils;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleTypeMetadata;
import com.cmeza.spring.jdbc.repository.repositories.definitions.MappingDefinition;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

public class JdbcBeanPropertyManager {
    private static final String TAG_SEPARATOR = ".";
    private static final int MAX_LEVEL = 5;
    private static final List<String> ignorePropertyNames = List.of("class", "bytes", "empty", "size", "entrySet");
    private final Map<String, MappingDefinition> additionalProperties = new HashMap<>();
    private final BeanWrapper beanWrapper;
    private String[] propertyNames;

    public JdbcBeanPropertyManager(Object object) {
        this.beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
    }

    public boolean hasValue(String paramName) {
        if (additionalProperties.containsKey(paramName)) {
            return true;
        }
        return this.beanWrapper.isReadableProperty(paramName);
    }

    public Object getValue(String paramName) throws IllegalArgumentException {
        try {
            if (additionalProperties.containsKey(paramName)) {
                return additionalProperties.get(paramName).getValue();
            }
            return this.beanWrapper.getPropertyValue(paramName);
        } catch (NotReadablePropertyException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public void addMappingDefinition(MappingDefinition mappingDefinition) {
        mappingDefinition.setValue(getValue(mappingDefinition.getFrom()));
        additionalProperties.put(mappingDefinition.getTo(), mappingDefinition);
    }

    public int getSqlType(String paramName) {
        if (additionalProperties.containsKey(paramName)) {
            return additionalProperties.get(paramName).getType();
        }
        Class<?> propType = this.beanWrapper.getPropertyType(paramName);
        return StatementCreatorUtils.javaTypeToSqlParameterType(propType);
    }

    public String[] getReadablePropertyNames() {
        if (this.propertyNames == null) {
            List<String> names = new ArrayList<>();
            PropertyDescriptor[] propertyDescriptors = this.beanWrapper.getPropertyDescriptors();
            this.iterate(propertyDescriptors, names, beanWrapper);
            names.addAll(additionalProperties.keySet());
            this.propertyNames = StringUtils.toStringArray(names);
        }

        return this.propertyNames;
    }

    private void iterate(PropertyDescriptor[] propertyDescriptors, List<String> names, BeanWrapper beanWrapper) {
        iterate(0, propertyDescriptors, names, "", beanWrapper);
    }

    private void iterate(int level, PropertyDescriptor[] propertyDescriptors, List<String> names, String prefix, BeanWrapper beanWrapper) {
        level++;
        if (level <= MAX_LEVEL) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                iterateDescriptors(level, propertyDescriptor, names, prefix, beanWrapper);
            }
        }
    }

    private void iterateDescriptors(int level, PropertyDescriptor propertyDescriptor, List<String> names, String prefix, BeanWrapper beanWrapper) {
        String name = propertyDescriptor.getName();
        if (!ignorePropertyNames.contains(name) && beanWrapper.isReadableProperty(name)) {
            TypeMetadata typeMetadata = new SimpleTypeMetadata(propertyDescriptor.getPropertyType());
            if (this.collectionsCondition(typeMetadata)) {
                return;
            }

            if (typeMetadata.isCustomArgumentObject() && !propertyDescriptor.getPropertyType().getPackageName().startsWith("java")) {
                this.evalCustomObject(names, level, name, prefix);
            } else {
                names.add(prefix + name);
            }
        }
    }

    private void evalCustomObject(List<String> names, int level, String name, String prefix) {
        BeanWrapper beanWrapperTarget = this.parseBeanWrapper(beanWrapper, name);
        if (Objects.nonNull(beanWrapperTarget)) {
            PropertyDescriptor[] propertyDescriptorsInternal = beanWrapperTarget.getPropertyDescriptors();
            if (propertyDescriptorsInternal.length > 0) {
                String internalPrefix = prefix + name + TAG_SEPARATOR;
                this.iterate(level, propertyDescriptorsInternal, names, internalPrefix, beanWrapperTarget);
            }
        } else {
            names.add(prefix + name);
        }
    }

    private BeanWrapper parseBeanWrapper(BeanWrapper beanWrapperOrigin, String propertyName) {
        try {
            Object object = beanWrapperOrigin.getPropertyValue(propertyName);
            if (Objects.isNull(object)) return null;
            return PropertyAccessorFactory.forBeanPropertyAccess(object);
        } catch (Exception e) {
            return null;
        }
    }

    private boolean collectionsCondition(TypeMetadata typeMetadata) {
        return typeMetadata.isList() || typeMetadata.isMap() || typeMetadata.isSet() || typeMetadata.isArray() || typeMetadata.isStream();
    }
}
