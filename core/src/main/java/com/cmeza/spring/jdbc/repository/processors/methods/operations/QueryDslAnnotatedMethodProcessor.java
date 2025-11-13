package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcQuery;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcQueryProperties;

public class QueryDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcQuery.DSL, JdbcQuery, JdbcQueryProperties> {
    public QueryDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new QueryAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcQuery generateEmptyAnnotation() {
        JdbcQueryProperties properties = new JdbcQueryProperties();
        properties.setTable("");
        return JdbcUtils.createAnnotation(JdbcQuery.class, properties.transform());
    }

    @Override
    protected Class<JdbcQuery.DSL> dslAnnotationType() {
        return JdbcQuery.DSL.class;
    }
}
