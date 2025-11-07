package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryDslRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.update.PostgresUpdateDslRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresUpdateDslTest extends AbstractUpdateTest {

    @Autowired
    public PostgresUpdateDslTest(PostgresUpdateDslRepository postgresUpdateDslRepository, PostgresQueryDslRepository postgresQueryDslRepository) {
        super(postgresUpdateDslRepository, postgresQueryDslRepository);
    }
}
