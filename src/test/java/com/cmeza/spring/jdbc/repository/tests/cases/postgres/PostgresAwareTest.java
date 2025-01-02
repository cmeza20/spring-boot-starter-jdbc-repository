package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.aware.PostgresJdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractAwareTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresAwareTest extends AbstractAwareTest {

    @Autowired
    public PostgresAwareTest(PostgresJdbcRepositoryAware postgresJdbcRepositoryAware) {
        super(postgresJdbcRepositoryAware);
    }

    @Override
    protected String qualifiedName() {
        return PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }

    @Override
    protected String dialectName() {
        return PostgresInitializer.JDBC_DIALECT_NAME;
    }
}
