package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.function;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultFunctionBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class MysqlFunctionBuilder extends DefaultFunctionBuilder {
    public MysqlFunctionBuilder(String routineName, Impl impl) {
        super(routineName, impl);
    }

    @Override
    public <T> T execute(Class<T> returnType) {
        if (hasOutParameter) {
            throw new UnsupportedOperationException("OUT parameter not supported for MySQL Function, use JdbcProcedure");
        }
        return super.execute(returnType);
    }

    @Override
    public <T> Optional<T> executeOptional() {
        throw new UnsupportedOperationException("Optional not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> Optional<T> executeOptional(Class<T> returnType) {
        throw new UnsupportedOperationException("Optional not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> List<T> executeList() {
        throw new UnsupportedOperationException("Return List not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> List<T> executeList(Class<T> returnType) {
        throw new UnsupportedOperationException("Return List not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> Set<T> executeSet() {
        throw new UnsupportedOperationException("Return Set not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> Set<T> executeSet(Class<T> returnType) {
        throw new UnsupportedOperationException("Return Set not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> Stream<T> executeStream() {
        throw new UnsupportedOperationException("Return Stream not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> Stream<T> executeStream(Class<T> returnType) {
        throw new UnsupportedOperationException("Return Stream not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public <T> T[] executeArray(Class<T> returnType) {
        throw new UnsupportedOperationException("Return Array not supported for MySQL Function, use JdbcProcedure");
    }

    @Override
    public Map<String, Object> executeMap() {
        throw new UnsupportedOperationException("Return Map not supported for MySQL Function, use JdbcProcedure");
    }
}
