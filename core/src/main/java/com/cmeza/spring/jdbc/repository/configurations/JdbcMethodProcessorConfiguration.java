package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedParameterProcessor;
import com.cmeza.spring.jdbc.repository.processors.methods.operations.*;
import com.cmeza.spring.jdbc.repository.processors.methods.supports.*;
import com.cmeza.spring.jdbc.repository.processors.parameters.ParamAnnotatedParameterProcessor;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.*;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.*;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.groups.JdbcJoinTables;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.groups.JdbcMappings;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;
import org.springframework.context.annotation.Bean;

public class JdbcMethodProcessorConfiguration {
    @Bean
    public AnnotatedMethodProcessor<JdbcFunction> functionAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new FunctionAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcInsert> insertAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new InsertAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcProcedure> procedureAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new ProcedureAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcQuery> queryAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new QueryAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcRawQuery> rawQueryAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new RawQueryAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcUpdate> updateAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new UpdateAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcRawUpdate> rawUpdateAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new RawUpdateAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcPagination> paginationAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new PaginationAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcRawPagination> rawPaginationAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new RawPaginationAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcExecute> executeAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new ExecuteAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcCall> callAnnotatedMethodProcessor(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new CallAnnotatedMethodProcessor(jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcMappings> mappingsAnnotatedMethodProcessor() {
        return new MappingsAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcMapping> mappingAnnotatedMethodProcessor() {
        return new MappingAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcJoinTables> joinTablesAnnotatedMethodProcessor() {
        return new JoinTablesAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcJoinTable> joinTableAnnotatedMethodProcessor() {
        return new JoinTableAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcFromTable> fromTableAnnotatedMethodProcessor() {
        return new FromTableAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcCountQuery> countQueryAnnotatedMethodProcessor() {
        return new CountQueryAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcRawCountQuery> rawCountQueryAnnotatedMethodProcessor() {
        return new RawCountQueryAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedParameterProcessor<JdbcParam> paramAnnotatedParameterProcessor() {
        return new ParamAnnotatedParameterProcessor();
    }
}
