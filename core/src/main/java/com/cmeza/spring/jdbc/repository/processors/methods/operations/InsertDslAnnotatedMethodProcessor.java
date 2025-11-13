package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcInsert;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcInsertProperties;

public class InsertDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcInsert.DSL, JdbcInsert, JdbcInsertProperties> {
    public InsertDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new InsertAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcInsert generateEmptyAnnotation() {
        JdbcInsertProperties properties = new JdbcInsertProperties();
        properties.setTable("");
        return JdbcUtils.createAnnotation(JdbcInsert.class, properties.transform());
    }

    @Override
    protected Class<JdbcInsert.DSL> dslAnnotationType() {
        return JdbcInsert.DSL.class;
    }
}
