package com.cmeza.spring.jdbc.repository.repositories.template.dialects.impl.procedure;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.DefaultProcedureBuilder;

public class PostgresProcedureBuilder extends DefaultProcedureBuilder {

    public PostgresProcedureBuilder(String routineName, Impl impl) {
        super(routineName, impl);
        throw new UnsupportedOperationException("@Procedure not supported for Postgres, use @Call");
    }

}
