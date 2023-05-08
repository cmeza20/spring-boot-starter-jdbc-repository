package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import org.springframework.jdbc.core.SqlOutParameter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface JdbcRoutineBuilder extends JdbcBuilder<JdbcRoutineBuilder> {
    <T> T execute();

    <T> T execute(Class<T> returnType);

    <T> List<T> executeList();

    <T> List<T> executeList(Class<T> returnType);

    <T> Set<T> executeSet();

    <T> Set<T> executeSet(Class<T> returnType);

    <T> Stream<T> executeStream();

    <T> Stream<T> executeStream(Class<T> returnType);

    <T> Optional<T> executeOptional();

    <T> Optional<T> executeOptional(Class<T> returnType);

    <T> T[] executeArray(Class<T> returnType);

    Map<String, Object> executeMap();

    JdbcRoutineBuilder withSchema(String schema);

    JdbcRoutineBuilder withCatalog(String catalog);

    JdbcRoutineBuilder withInParameterNames(String... inParameterNames);

    JdbcRoutineBuilder withOutParameters(SqlOutParameter... outParameters);

    JdbcRoutineBuilder withOutParameter(String parameter, int sqlType);

    JdbcRoutineBuilder withAccessCallParameterMetaData(boolean value);

}
