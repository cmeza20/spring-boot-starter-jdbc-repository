package com.cmeza.spring.jdbc.repository.support.factories;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import org.slf4j.Logger;

public interface JdbcCallFactory {

    JdbcCallFactory withSchema(String schema);

    JdbcCallFactory withCatalog(String catalog);

    JdbcCallFactory withParameters(String... parameters);

    JdbcCallFactory withType(JdbcCall.CallType type);

    void print();

    void print(Logger log);

    String generateCallQuery();

}
