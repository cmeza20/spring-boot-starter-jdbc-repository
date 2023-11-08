package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.beans.factory.Aware;

import java.util.Map;

public interface JdbcRepositoryAware extends Aware {
    default void setPropertiesResolver(JdbcPropertyResolver propertiesResolver) {
    }

    default void setJdbcRepositoryTemplate(JdbcRepositoryTemplate jdbcRepositoryTemplate){
    }

    default void setJdbcRepositoryTemplate(Map<String, JdbcRepositoryTemplate> jdbcRepositoryTemplates){
    }

    default void setNamingStrategy(NamingStrategy namingStrategy) {
    }
}
