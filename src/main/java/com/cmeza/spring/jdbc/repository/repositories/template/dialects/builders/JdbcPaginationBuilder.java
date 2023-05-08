package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;

public interface JdbcPaginationBuilder extends JdbcBuilder<JdbcPaginationBuilder> {
    JdbcPaginationBuilder withPageRequest(JdbcPageRequest pageRequest);

    JdbcPaginationBuilder withCountQuery(String customCountquery);

    <R> JdbcPage<R> fetchPage();

    <R> JdbcPage<R> fetchPage(Class<R> resultType);

    <R> JdbcPage<R> fetchPage(JdbcPageRequest pageRequest);

    <R> JdbcPage<R> fetchPage(JdbcPageRequest pageRequest, Class<R> resultType);
}
