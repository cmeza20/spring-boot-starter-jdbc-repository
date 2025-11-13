package com.cmeza.spring.jdbc.repository.processors.methods.operations;

import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.methods.abstracts.AbstractAnnotatedDslMethodProcessor;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcPagination;
import com.cmeza.spring.jdbc.repository.support.properties.methods.JdbcPaginationProperties;

public class PaginationDslAnnotatedMethodProcessor extends AbstractAnnotatedDslMethodProcessor<JdbcPagination.DSL, JdbcPagination, JdbcPaginationProperties> {
    public PaginationDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        super(new PaginationAnnotatedMethodProcessor(jdbcRepositoryProperties));
    }

    @Override
    protected JdbcPagination generateEmptyAnnotation() {
        JdbcPaginationProperties properties = new JdbcPaginationProperties();
        properties.setTable("");
        return JdbcUtils.createAnnotation(JdbcPagination.class, properties.transform());
    }

    @Override
    protected Class<JdbcPagination.DSL> dslAnnotationType() {
        return JdbcPagination.DSL.class;
    }
}
