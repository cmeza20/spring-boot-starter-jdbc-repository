package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.ioc.handler.handlers.IocMethodInterceptor;
import com.cmeza.spring.jdbc.repository.interceptors.JdbcPropertyResolverInterceptor;
import com.cmeza.spring.jdbc.repository.interceptors.JdbcRepositoryTemplateMethodInterceptor;
import com.cmeza.spring.jdbc.repository.interceptors.JdbcTemplateMethodInterceptor;
import com.cmeza.spring.jdbc.repository.interceptors.NamedParameterJdbcTemplateMethodInterceptor;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcMethodInterceptorsConfiguration {
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
    public IocMethodInterceptor<JdbcPropertyResolver> jdbcPropertyResolverMethodInterceptor() {
        return new JdbcPropertyResolverInterceptor();
    }
}
