package com.cmeza.spring.jdbc.repository.tests.cases.postgres.raw;

import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresRawQueryDslRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractQueryTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class PostgresRawQueryDslTest extends AbstractQueryTest {

    @Autowired
    public PostgresRawQueryDslTest(PostgresRawQueryDslRepository postgresRawQueryRepository) {
        super(postgresRawQueryRepository);
    }
}
