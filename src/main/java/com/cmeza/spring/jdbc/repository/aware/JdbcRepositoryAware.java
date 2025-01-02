package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.beans.factory.Aware;

public interface JdbcRepositoryAware extends Aware {
    default String getQualifier() {
        return "jdbcRepositoryTemplate";
    }

    default boolean findQualified() {
        return true;
    }

    void setPropertiesResolver(JdbcPropertyResolver propertiesResolver);


    void setJdbcRepositoryTemplate(JdbcRepositoryTemplate jdbcRepositoryTemplate);

    void setNamingStrategy(NamingStrategy namingStrategy);
}
