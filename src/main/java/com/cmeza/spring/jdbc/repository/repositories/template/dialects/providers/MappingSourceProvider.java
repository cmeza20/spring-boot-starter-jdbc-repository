package com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers;

import com.cmeza.spring.jdbc.repository.repositories.definitions.MappingDefinition;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MappingSourceProvider {
    private final List<MappingDefinition> mappingDefinitions = new ArrayList<>();

    public MappingSourceProvider addMapping(String to, String from, int sqlType) {
        if (!StringUtils.isEmpty(to)) {
            mappingDefinitions.add(
                    new MappingDefinition()
                            .setFrom(from)
                            .setTo(to)
                            .setType(sqlType)
                            .setPosition(mappingDefinitions.size()));
        }
        return this;
    }

    public MappingSourceProvider addMapping(MappingDefinition mappingDefinition) {
        if (Objects.nonNull(mappingDefinition)) {
            mappingDefinitions.add(mappingDefinition);
        }
        return this;
    }

    public Optional<MappingDefinition> findMappingByFrom(String from) {
        return mappingDefinitions.stream().filter(m -> {
            if (!StringUtils.isEmpty(m.getFrom())) {
                return m.getFrom().equals(from);
            }
            return m.getTo().equals(from);
        }).findFirst();
    }

    public Optional<MappingDefinition> findMappingByFromOrTo(String from) {
        return mappingDefinitions.stream().filter(m -> m.getTo().equals(from) || m.getFrom().equals(from)).findFirst();
    }

    public List<MappingDefinition> getSortedMappingDefinitions() {
        return mappingDefinitions.stream().sorted(Comparator.comparing(MappingDefinition::getPosition)).collect(Collectors.toList());
    }

    public boolean isSetMappings() {
        return !mappingDefinitions.isEmpty();
    }
}
