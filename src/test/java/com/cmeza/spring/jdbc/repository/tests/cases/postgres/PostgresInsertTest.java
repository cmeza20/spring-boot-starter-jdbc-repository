package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.PostgresExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.PostgresInsertRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractInsertTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresInsertTest extends AbstractInsertTest {

    @Autowired
    public PostgresInsertTest(PostgresInsertRepository postgresInsertRepository, PostgresQueryRepository postgresQueryRepository, PostgresExecuteRepository postgresExecuteRepository) {
        super(postgresInsertRepository, postgresQueryRepository, postgresExecuteRepository);
    }
}
