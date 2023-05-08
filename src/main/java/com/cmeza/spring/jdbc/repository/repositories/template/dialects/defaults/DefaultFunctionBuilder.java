package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractRoutineBuilder;
import org.slf4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class DefaultFunctionBuilder extends AbstractRoutineBuilder {

    public DefaultFunctionBuilder(String routineName, Impl impl) {
        super(routineName, impl);
    }

    @Override
    protected void printExtrasRoutine(Logger logger) {
        logger.debug("| Function: {}", routineName);
    }

    @Override
    protected void configureSimpleJdbcCall(SimpleJdbcCall simpleJdbcCall) {
        simpleJdbcCall.withFunctionName(routineName);
    }

    @Override
    protected void printParameter(Logger logger, AbstractRoutineBuilder.Direction direction, String name, String typeName) {
        logger.debug("| Function Parameter: [{} - {} - {}]", direction, name, typeName);
    }

}
