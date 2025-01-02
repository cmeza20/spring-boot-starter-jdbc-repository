package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories;

import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcCall;
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
