package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcUpdateProperties;

public class UpdateDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcUpdate.DSL, JdbcUpdate, JdbcUpdateProperties> {
    public UpdateDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new UpdateAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcUpdate generateEmptyAnnotation() {
        JdbcUpdateProperties properties = new JdbcUpdateProperties();
        properties.setTable("");
        properties.setUpdateSets(new String[0]);
        return JdbcUtils.createAnnotation(JdbcUpdate.class, properties.transform());
    }

    @Override
    protected Class<JdbcUpdate.DSL> dslAnnotationType() {
        return JdbcUpdate.DSL.class;
    }
}
