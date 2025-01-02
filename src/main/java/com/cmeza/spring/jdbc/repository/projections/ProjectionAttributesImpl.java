package com.cmeza.spring.jdbc.repository.projections;

import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProjectionAttributesImpl implements ProjectionAttributes {
    private final Map<String, Object> map = new LinkedHashMap<>();
    private final List<PropertyDescriptor> descriptors;

    public ProjectionAttributesImpl(List<PropertyDescriptor> descriptors) {
        Assert.notNull(descriptors, "List of descriptors must not be null");
        this.descriptors = descriptors;
    }

    @Override
    public ProjectionAttributes addAttribute(String name, Object value) {
        Assert.isTrue(descriptors.stream().anyMatch(d -> d.getName().equals(name)), String.format("Attribute %s is not present in the projection", name));
        map.put(name, value);
        return this;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return map;
    }
}
