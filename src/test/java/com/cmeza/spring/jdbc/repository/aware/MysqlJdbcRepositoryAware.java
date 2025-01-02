package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.MysqlInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({TestConstants.MYSQL, TestConstants.ALL})
public class MysqlJdbcRepositoryAware extends AbstractJdbcRepositoryAware {
    @Override
    public String getQualifier() {
        return MysqlInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }
}
