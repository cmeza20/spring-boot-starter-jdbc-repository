package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.update.PostgresUpdateRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class PostgresUpdateTest extends AbstractUpdateTest {

    @Autowired
    public PostgresUpdateTest(PostgresUpdateRepository postgresUpdateRepository, PostgresQueryRepository postgresQueryRepository) {
        super(postgresUpdateRepository, postgresQueryRepository);
    }
}
