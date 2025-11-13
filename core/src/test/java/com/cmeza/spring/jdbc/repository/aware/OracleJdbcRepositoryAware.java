package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({TestConstants.ORACLE, TestConstants.ALL})
public class OracleJdbcRepositoryAware extends AbstractJdbcRepositoryAware {
    @Override
    public String getQualifier() {
        return OracleInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }
}
