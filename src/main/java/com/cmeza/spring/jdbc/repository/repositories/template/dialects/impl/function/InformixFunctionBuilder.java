package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.function;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultFunctionBuilder;

public class InformixFunctionBuilder extends DefaultFunctionBuilder {
    public InformixFunctionBuilder(String routineName, Impl impl) {
        super(routineName, impl);
        throw new UnsupportedOperationException("@Function: Informix Dynamic Server is not one of the databases fully supported for function calls");
    }
}
