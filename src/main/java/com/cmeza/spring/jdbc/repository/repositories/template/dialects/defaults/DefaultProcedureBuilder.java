package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.Direction;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractRoutineBuilder;
import org.slf4j.Logger;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class DefaultProcedureBuilder extends AbstractRoutineBuilder {

    public DefaultProcedureBuilder(String routineName, Impl impl) {
        super(routineName, impl);
    }

    @Override
    protected void printExtrasRoutine(Logger logger) {
        logger.info("| Procedure: {}", routineName);
    }

    @Override
    protected void configureSimpleJdbcCall(SimpleJdbcCall simpleJdbcCall) {
        simpleJdbcCall.withProcedureName(routineName);
        simpleJdbcCall.setFunction(false);
    }

    @Override
    protected void printParameter(Logger logger, Direction direction, String name, String typeName) {
        logger.info("| Procedure Parameter: [{} - {} - {}]", direction, name, typeName);
    }

}
