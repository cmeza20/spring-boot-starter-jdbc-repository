package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.procedure;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultProcedureBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class OracleProcedureBuilder extends DefaultProcedureBuilder {

    public OracleProcedureBuilder(String routineName, Impl impl) {
        super(routineName, impl);
    }

    @Override
    public <T> List<T> executeList() {
        throw new UnsupportedOperationException("Return List not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> List<T> executeList(Class<T> returnType) {
        throw new UnsupportedOperationException("Return List not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> Set<T> executeSet() {
        throw new UnsupportedOperationException("Return Set not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> Set<T> executeSet(Class<T> returnType) {
        throw new UnsupportedOperationException("Return Set not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> Stream<T> executeStream() {
        throw new UnsupportedOperationException("Return Stream not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> Stream<T> executeStream(Class<T> returnType) {
        throw new UnsupportedOperationException("Return Stream not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> Optional<T> executeOptional() {
        throw new UnsupportedOperationException("Optional not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> Optional<T> executeOptional(Class<T> returnType) {
        throw new UnsupportedOperationException("Optional not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public <T> T[] executeArray(Class<T> returnType) {
        throw new UnsupportedOperationException("Return Array not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Override
    public Map<String, Object> executeMap() {
        throw new UnsupportedOperationException("Return Map not supported for Oracle Procedure, use JdbcRawQuery");
    }
}
