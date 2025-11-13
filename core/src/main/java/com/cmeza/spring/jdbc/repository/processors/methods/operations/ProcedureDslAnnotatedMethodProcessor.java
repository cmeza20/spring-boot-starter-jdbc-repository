package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcProcedureProperties;

public class ProcedureDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcProcedure.DSL, JdbcProcedure, JdbcProcedureProperties> {
    public ProcedureDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new ProcedureAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcProcedure generateEmptyAnnotation() {
        JdbcProcedureProperties properties = new JdbcProcedureProperties();
        properties.setName("");
        return JdbcUtils.createAnnotation(JdbcProcedure.class, properties.transform());
    }

    @Override
    protected Class<JdbcProcedure.DSL> dslAnnotationType() {
        return JdbcProcedure.DSL.class;
    }
}
