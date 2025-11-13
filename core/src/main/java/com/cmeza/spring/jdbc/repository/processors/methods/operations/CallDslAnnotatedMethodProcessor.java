package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcCallProperties;

public class CallDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcCall.DSL, JdbcCall, JdbcCallProperties> {
    public CallDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new CallAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcCall generateEmptyAnnotation() {
        JdbcCallProperties properties = new JdbcCallProperties();
        properties.setValue("");
        return JdbcUtils.createAnnotation(JdbcCall.class, properties.transform());
    }

    @Override
    protected Class<JdbcCall.DSL> dslAnnotationType() {
        return JdbcCall.DSL.class;
    }
}
