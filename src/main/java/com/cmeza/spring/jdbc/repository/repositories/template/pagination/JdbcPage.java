package com.cmeza.spring.jdbc.repository.repositories.template.pagination;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"content", "first", "last", "currentPage", "totalElements", "totalPages"})
public interface JdbcPage<T> {
    int getCurrentPage();

    int getTotalPages();

    long getTotalElements();

    List<T> getContent();

    boolean isFirst();

    boolean isLast();
}
