package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.Direction;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractRoutineBuilder;
import org.slf4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class DefaultFunctionBuilder extends AbstractRoutineBuilder {

    public DefaultFunctionBuilder(String routineName, Impl impl) {
        super(routineName, impl);
    }

    @Override
    protected void printExtrasRoutine(Logger logger) {
        logger.info("| Function: {}", routineName);
    }

    @Override
    protected void configureSimpleJdbcCall(SimpleJdbcCall simpleJdbcCall) {
        simpleJdbcCall.withFunctionName(routineName);
        simpleJdbcCall.setFunction(true);
    }

    @Override
    protected void printParameter(Logger logger, Direction direction, String name, String typeName) {
        logger.info("| Function Parameter: [{} - {} - {}]", direction, name, typeName);
    }

}
