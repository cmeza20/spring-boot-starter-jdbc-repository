package com.cmeza.spring.jdbc.repository.support.definitions;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import lombok.Data;

@Data
public class ParameterDefinition {
    private String parameterName;
    private int parameterType;
    private String parameterTypeName;
    private int position;
    private boolean isBean;
    private boolean isBatch;
    private boolean isArray;
    private boolean isPageRequest;
    private boolean isCollection;
    private TypeMetadata typeMetadata;
}
