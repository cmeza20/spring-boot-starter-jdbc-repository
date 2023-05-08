package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.ioc.handler.builders.Ioc;
import com.cmeza.spring.ioc.handler.configuration.IocAutoConfiguration;
import com.cmeza.spring.ioc.handler.contracts.IocContract;
import com.cmeza.spring.ioc.handler.handlers.IocMethodInterceptor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedClassProcessor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedParameterProcessor;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.*;
import com.cmeza.spring.jdbc.repository.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.aware.AwareBeanPostProcessor;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContract;
import com.cmeza.spring.jdbc.repository.interceptors.JdbcRepositoryTemplateMethodInterceptor;
import com.cmeza.spring.jdbc.repository.interceptors.JdbcTemplateMethodInterceptor;
import com.cmeza.spring.jdbc.repository.interceptors.NamedParameterJdbcTemplateMethodInterceptor;
import com.cmeza.spring.jdbc.repository.processors.classes.RepositoryAnnotatedClassProcessor;
import com.cmeza.spring.jdbc.repository.processors.methods.*;
import com.cmeza.spring.jdbc.repository.processors.parameters.ParamAnnotatedParameterProcessor;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolverImpl;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@AutoConfigureOrder(Ioc.IOC_ORDER + 1)
@AutoConfiguration(before = IocAutoConfiguration.class)
@EnableConfigurationProperties(JdbcRepositoryProperties.class)
public class JdbcRepositoryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(DataSource.class)
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Primary
    @Bean("jdbcRepositoryTemplate")
    @ConditionalOnBean(DataSource.class)
    public JdbcRepositoryTemplate jdbcRepositoryTemplate(DataSource dataSource) {
        return new JdbcRepositoryTemplate(dataSource);
    }

    @Bean
    public IocContract<JdbcRepository> jdbcContract(List<AnnotatedClassProcessor<?>> classProcessors, List<AnnotatedMethodProcessor<?>> methodProcessors, List<AnnotatedParameterProcessor<?>> parameterProcessors, JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new JdbcContract(classProcessors, methodProcessors, parameterProcessors, jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedClassProcessor<JdbcRepository> repositoryAnnotatedClassProcessor() {
        return new RepositoryAnnotatedClassProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcBatchUpdate> batchAnnotatedMethodProcessor() {
        return new BatchUpdateAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcFunction> functionAnnotatedMethodProcessor() {
        return new FunctionAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcInsert> insertAnnotatedMethodProcessor() {
        return new InsertAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcProcedure> procedureAnnotatedMethodProcessor() {
        return new ProcedureAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcQuery> queryAnnotatedMethodProcessor() {
        return new QueryAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcUpdate> updateAnnotatedMethodProcessor() {
        return new UpdateAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedMethodProcessor<JdbcPagination> paginationAnnotatedMethodProcessor() {
        return new PaginationAnnotatedMethodProcessor();
    }

    @Bean
    public AnnotatedParameterProcessor<JdbcParam> paramAnnotatedParameterProcessor() {
        return new ParamAnnotatedParameterProcessor();
    }

    @Bean
    public IocMethodInterceptor<JdbcTemplate> jdbcTemplateMethodInterceptor() {
        return new JdbcTemplateMethodInterceptor();
    }

    @Bean
    public IocMethodInterceptor<JdbcRepositoryTemplate> jdbcRepositoryTemplateMethodInterceptor() {
        return new JdbcRepositoryTemplateMethodInterceptor();
    }

    @Bean
    public IocMethodInterceptor<NamedParameterJdbcTemplate> namedParameterJdbcTemplateMethodInterceptor() {
        return new NamedParameterJdbcTemplateMethodInterceptor();
    }

    @Bean
    public JdbcPropertyResolver jdbcPropertyResolver(Environment environment) {
        return new JdbcPropertyResolverImpl(environment);
    }

    @Bean
    public BeanPostProcessor awareBeanPostProcessor(ApplicationContext applicationContext) {
        return new AwareBeanPostProcessor(applicationContext);
    }
}
