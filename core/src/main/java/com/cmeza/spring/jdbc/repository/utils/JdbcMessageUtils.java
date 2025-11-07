package com.cmeza.spring.jdbc.repository.utils;

import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JdbcMessageUtils {
    public String validateAnnotationMessage(MethodMetadata methodMetadata, Class<?> classAnnotation, String attributeName) {
        return methodMetadata.getConfigKey() + "- The '" + attributeName + "' attribute is required in the @" + classAnnotation.getSimpleName() + " annotation";
    }
}
