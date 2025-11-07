package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.InformixInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({TestConstants.INFORMIX, TestConstants.ALL})
public class InformixJdbcRepositoryAware extends AbstractJdbcRepositoryAware {
    @Override
    public String getQualifier() {
        return InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }
}
