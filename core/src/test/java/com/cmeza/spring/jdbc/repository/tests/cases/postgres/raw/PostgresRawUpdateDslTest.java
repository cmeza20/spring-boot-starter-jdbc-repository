package com.cmeza.spring.jdbc.repository.tests.cases.postgres.raw;

import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryDslRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.update.PostgresRawUpdateDslRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class PostgresRawUpdateDslTest extends AbstractUpdateTest {

    @Autowired
    public PostgresRawUpdateDslTest(PostgresRawUpdateDslRepository postgresRawUpdateDslRepository, PostgresQueryDslRepository postgresQueryDslRepository) {
        super(postgresRawUpdateDslRepository, postgresQueryDslRepository);
    }
}
