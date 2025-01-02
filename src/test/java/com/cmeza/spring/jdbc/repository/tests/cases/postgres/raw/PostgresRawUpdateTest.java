package com.cmeza.spring.jdbc.repository.tests.cases.postgres.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.update.PostgresRawUpdateRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresRawUpdateTest extends AbstractUpdateTest {

    @Autowired
    public PostgresRawUpdateTest(PostgresRawUpdateRepository postgresRawQueryRepository, PostgresQueryRepository postgresQueryRepository) {
        super(postgresRawQueryRepository, postgresQueryRepository);
    }
}
