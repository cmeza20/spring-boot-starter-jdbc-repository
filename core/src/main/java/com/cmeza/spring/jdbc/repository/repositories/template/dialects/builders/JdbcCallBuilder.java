package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;

public interface JdbcCallBuilder extends JdbcGenericBuilder<JdbcCallBuilder> {
    void execute();

    JdbcCallBuilder withSchema(String schema);

    JdbcCallBuilder withCatalog(String catalog);

    JdbcCallBuilder withParameters(String... parameters);

    JdbcCallBuilder withType(JdbcCall.CallType type);
}
