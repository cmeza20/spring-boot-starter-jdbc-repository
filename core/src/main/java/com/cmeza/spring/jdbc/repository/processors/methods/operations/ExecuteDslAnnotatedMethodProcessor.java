package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcExecuteProperties;

public class ExecuteDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcExecute.DSL, JdbcExecute, JdbcExecuteProperties> {
    public ExecuteDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new ExecuteAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcExecute generateEmptyAnnotation() {
        JdbcExecuteProperties properties = new JdbcExecuteProperties();
        properties.setValue("");
        return JdbcUtils.createAnnotation(JdbcExecute.class, properties.transform());
    }

    @Override
    protected Class<JdbcExecute.DSL> dslAnnotationType() {
        return JdbcExecute.DSL.class;
    }
}
