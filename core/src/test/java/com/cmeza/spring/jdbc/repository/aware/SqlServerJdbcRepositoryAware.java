package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.SqlServerInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({TestConstants.SQLSERVER, TestConstants.ALL})
public class SqlServerJdbcRepositoryAware extends AbstractJdbcRepositoryAware {
    @Override
    public String getQualifier() {
        return SqlServerInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }
}
