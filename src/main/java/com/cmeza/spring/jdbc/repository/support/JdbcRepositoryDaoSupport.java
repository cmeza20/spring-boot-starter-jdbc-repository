package com.cmeza.spring.jdbc.repository.support;

import com.cmeza.spring.jdbc.repository.aware.JdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.beans.BeanInstantiationException;

import java.util.Map;
import java.util.Objects;

public abstract class JdbcRepositoryDaoSupport implements JdbcRepositoryAware {
    protected JdbcPropertyResolver resolver;
    protected JdbcRepositoryTemplate template;

    protected String getQualifier() {
        return null;
    }

    @Override
    public void setPropertiesResolver(JdbcPropertyResolver propertiesResolver) {
        this.resolver = propertiesResolver;
    }

    @Override
    public void setJdbcRepositoryTemplate(JdbcRepositoryTemplate jdbcRepositoryTemplate) {
        if (Objects.isNull(getQualifier())) {
            this.template = jdbcRepositoryTemplate;
            this.validateJdbcRepositoryTemplate();
        }
    }

    @Override
    public void setJdbcRepositoryTemplate(Map<String, JdbcRepositoryTemplate> jdbcRepositoryTemplates) {
        String qualifier = getQualifier();
        if (Objects.nonNull(qualifier)) {
            this.template = jdbcRepositoryTemplates.get(qualifier);
            this.validateJdbcRepositoryTemplate();
        }
    }

    private void validateJdbcRepositoryTemplate() {
        if (Objects.isNull(this.template)) {
            String qualifier = getQualifier();
            if (Objects.nonNull(qualifier)) {
                throw new BeanInstantiationException(this.getClass(), "No bean qualifier: " + qualifier);
            } else {
                throw new BeanInstantiationException(this.getClass(), "No bean qualifier for: " + JdbcRepositoryTemplate.class.getSimpleName());
            }
        }
    }
}
