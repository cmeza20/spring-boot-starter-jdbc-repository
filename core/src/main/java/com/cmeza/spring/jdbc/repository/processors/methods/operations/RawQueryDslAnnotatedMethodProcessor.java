package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawQuery;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawQueryProperties;

public class RawQueryDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcRawQuery.DSL, JdbcRawQuery, JdbcRawQueryProperties> {
    public RawQueryDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new RawQueryAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcRawQuery generateEmptyAnnotation() {
        JdbcRawQueryProperties properties = new JdbcRawQueryProperties();
        properties.setValue("");
        return JdbcUtils.createAnnotation(JdbcRawQuery.class, properties.transform());
    }

    @Override
    protected Class<JdbcRawQuery.DSL> dslAnnotationType() {
        return JdbcRawQuery.DSL.class;
    }
}
