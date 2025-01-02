package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface JdbcQueryBuilder extends JdbcGenericBuilder<JdbcQueryBuilder> {
    <R> R fetchOne();

    <R> R fetchOne(Class<R> resultType);

    <R> Optional<R> fetchOptional();

    <R> Optional<R> fetchOptional(Class<R> resultType);

    <R> List<R> fetchList();

    <R> List<R> fetchList(Class<R> resultType);

    <R> Set<R> fetchSet();

    <R> Set<R> fetchSet(Class<R> resultType);

    <R> Stream<R> fetchStream();

    <R> Stream<R> fetchStream(Class<R> resultType);

    <R> R[] fetchArray(Class<R> resultType);

    Map<String, Object> fetchMap();
}
