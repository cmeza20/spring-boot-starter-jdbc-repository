package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcFunction;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcFunctionProperties;

public class FunctionDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcFunction.DSL, JdbcFunction, JdbcFunctionProperties> {
    public FunctionDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new FunctionAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcFunction generateEmptyAnnotation() {
        JdbcFunctionProperties properties = new JdbcFunctionProperties();
        properties.setName("");
        return JdbcUtils.createAnnotation(JdbcFunction.class, properties.transform());
    }

    @Override
    protected Class<JdbcFunction.DSL> dslAnnotationType() {
        return JdbcFunction.DSL.class;
    }
}
