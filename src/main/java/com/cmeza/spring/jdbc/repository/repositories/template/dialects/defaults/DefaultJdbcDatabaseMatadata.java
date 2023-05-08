package com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultJdbcDatabaseMatadata implements JdbcDatabaseMatadata {

    private final String databaseProductName;
    private final String databaseProductVersion;
}
