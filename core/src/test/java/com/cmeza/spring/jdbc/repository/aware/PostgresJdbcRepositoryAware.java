package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({TestConstants.POSTGRES, TestConstants.ALL})
public class PostgresJdbcRepositoryAware extends AbstractJdbcRepositoryAware {
    @Override
    public String getQualifier() {
        return PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }
}
