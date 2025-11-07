package com.cmeza.spring.jdbc.repository.repositories.template.dialects;

import org.springframework.boot.jdbc.DatabaseDriver;

import java.util.Arrays;

public enum Dialect {

    DEFAULT(DatabaseDriver.UNKNOWN),
    MYSQL(DatabaseDriver.MYSQL),
    MARIADB(DatabaseDriver.MARIADB),
    SQLITE(DatabaseDriver.SQLITE),
    INFORMIX(DatabaseDriver.INFORMIX),
    ORACLE(DatabaseDriver.ORACLE),
    POSTGRESQL(DatabaseDriver.POSTGRESQL),
    SQLSERVER(DatabaseDriver.SQLSERVER);

    final DatabaseDriver databaseDriver;

    Dialect(DatabaseDriver databaseDriver) {
        this.databaseDriver = databaseDriver;
    }

    public static Dialect from(DatabaseDriver databaseDriver) {
        return Arrays.stream(Dialect.values())
                .filter(d -> d.getDatabaseDriver().equals(databaseDriver))
                .findFirst()
                .orElse(DEFAULT);
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }
}
