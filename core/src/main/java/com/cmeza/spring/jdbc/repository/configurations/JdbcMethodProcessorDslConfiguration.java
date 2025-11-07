package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.jdbc.repository.processors.methods.operations.*;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.*;
import org.springframework.context.annotation.Bean;

public class JdbcMethodProcessorDslConfiguration {
    @Bean
    public AnnotatedMethodProcessor<JdbcFunction.DSL> functionDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new FunctionDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcInsert.DSL> insertDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new InsertDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcProcedure.DSL> procedureDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new ProcedureDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcQuery.DSL> queryDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new QueryDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcRawQuery.DSL> rawQueryDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new RawQueryDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcUpdate.DSL> updateDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new UpdateDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcRawUpdate.DSL> rawUpdateDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new RawUpdateDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcPagination.DSL> paginationDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new PaginationDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcRawPagination.DSL> rawPaginationDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new RawPaginationDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcExecute.DSL> executeDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new ExecuteDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcCall.DSL> callDslAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new CallDslAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }
}
