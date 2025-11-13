package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawUpdateProperties;

public class RawUpdateDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcRawUpdate.DSL, JdbcRawUpdate, JdbcRawUpdateProperties> {
    public RawUpdateDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new RawUpdateAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcRawUpdate generateEmptyAnnotation() {
        JdbcRawUpdateProperties properties = new JdbcRawUpdateProperties();
        properties.setValue("");
        return JdbcUtils.createAnnotation(JdbcRawUpdate.class, properties.transform());
    }

    @Override
    protected Class<JdbcRawUpdate.DSL> dslAnnotationType() {
        return JdbcRawUpdate.DSL.class;
    }
}
