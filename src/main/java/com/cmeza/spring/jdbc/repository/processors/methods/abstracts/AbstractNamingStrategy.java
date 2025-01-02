package com.cmeza.spring.jdbc.repository.processors.methods.abstracts;

import com.cmeza.spring.jdbc.repository.aware.JdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractNamingStrategy implements JdbcRepositoryAware {
    protected NamingStrategy globalNamingStrategy;

    @Override
    public void setNamingStrategy(NamingStrategy namingStrategy) {
        this.globalNamingStrategy = namingStrategy;
    }

    protected NamingStrategy extractNamingStrategy(Class<? extends NamingStrategy> customNamingStrategyClass) {
        NamingStrategy customNamingStrategy = Objects.nonNull(customNamingStrategyClass) ? BeanUtils.instantiateClass(customNamingStrategyClass) : null;
        if (Objects.nonNull(customNamingStrategy) && !customNamingStrategyClass.isAssignableFrom(NoOpNamingStrategy.class)) {
            return customNamingStrategy;
        } else if (Objects.nonNull(globalNamingStrategy)) {
            return globalNamingStrategy;
        } else {
            return new NoOpNamingStrategy();
        }
    }

    protected String[] executeNamingStrategy(String[] origins, NamingStrategy customNamingStrategy) {
        return Arrays.stream(origins).map(customNamingStrategy::parse).toArray(String[]::new);
    }

    @Override
    public void setJdbcRepositoryTemplate(JdbcRepositoryTemplate jdbcRepositoryTemplate) {

    }

    @Override
    public boolean findQualified() {
        return false;
    }
}
