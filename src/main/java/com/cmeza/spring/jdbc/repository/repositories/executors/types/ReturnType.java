package com.cmeza.spring.jdbc.repository.repositories.executors.types;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public enum ReturnType {
    LIST(List.class),
    SET(Set.class),
    MAP(Map.class),
    STREAM(Stream.class),
    OPTIONAL(Optional.class),
    ARRAY(Array.class),
    VOID(Void.class),
    ENUM(Enum.class),
    MAP_ENTRY(Map.Entry.class),
    INTEGER(Integer.class),
    PAGE(JdbcPage.class),
    KEY_HOLDER(KeyHolder.class),
    CUSTOM_OBJECT(CustomResult.class);

    final Class<?> clazz;

    ReturnType(Class<?> clazz) {
        this.clazz = clazz;
    }

    public static ReturnType from(TypeMetadata typeMetadata) {
        if (typeMetadata.isList()) {
            return LIST;
        }
        if (typeMetadata.isSet()) {
            return SET;
        }
        if (typeMetadata.isStream()) {
            return STREAM;
        }
        if (typeMetadata.isMap()) {
            return MAP;
        }
        if (typeMetadata.isOptional()) {
            return OPTIONAL;
        }
        if (typeMetadata.isArray()) {
            return ARRAY;
        }
        if (typeMetadata.isVoid()) {
            return VOID;
        }
        if (typeMetadata.isEnum()) {
            return ENUM;
        }
        if (typeMetadata.isMapEntry()) {
            return MAP_ENTRY;
        }
        if (typeMetadata.getRawClass().isAssignableFrom(int.class) || typeMetadata.getRawClass().isAssignableFrom(Integer.class)) {
            return INTEGER;
        }
        if (typeMetadata.getRawClass().isAssignableFrom(KeyHolder.class)) {
            return KEY_HOLDER;
        }
        if (typeMetadata.getRawClass().isAssignableFrom(JdbcPage.class)) {
            return PAGE;
        }
        return CUSTOM_OBJECT;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static final class CustomResult {
    }

}
