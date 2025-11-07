package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import lombok.Getter;

@Getter
public abstract class AbstractJdbcRepositoryAware implements JdbcRepositoryAware {

    private JdbcRepositoryTemplate jdbcRepositoryTemplate;
    private NamingStrategy namingStrategy;
    private JdbcPropertyResolver jdbcPropertyResolver;

    @Override
    public void setPropertiesResolver(JdbcPropertyResolver jdbcPropertyResolver) {
        this.jdbcPropertyResolver = jdbcPropertyResolver;
    }

    @Override
    public void setJdbcRepositoryTemplate(JdbcRepositoryTemplate jdbcRepositoryTemplate) {
        this.jdbcRepositoryTemplate = jdbcRepositoryTemplate;
    }

    @Override
    public void setNamingStrategy(NamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }
}
