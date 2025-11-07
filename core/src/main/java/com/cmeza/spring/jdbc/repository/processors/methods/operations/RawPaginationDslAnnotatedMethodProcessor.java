package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawPagination;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcRawPaginationProperties;

public class RawPaginationDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcRawPagination.DSL, JdbcRawPagination, JdbcRawPaginationProperties> {
    public RawPaginationDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new RawPaginationAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcRawPagination generateEmptyAnnotation() {
        JdbcRawPaginationProperties properties = new JdbcRawPaginationProperties();
        properties.setValue("");
        return JdbcUtils.createAnnotation(JdbcRawPagination.class, properties.transform());
    }

    @Override
    protected Class<JdbcRawPagination.DSL> dslAnnotationType() {
        return JdbcRawPagination.DSL.class;
    }
}
