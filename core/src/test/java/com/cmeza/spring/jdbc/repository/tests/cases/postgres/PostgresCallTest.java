package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.call.PostgresCallRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.execute.PostgresExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractCallTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresCallTest extends AbstractCallTest {

    @Autowired
    public PostgresCallTest(PostgresCallRepository postgresCallRepository, PostgresQueryRepository postgresQueryRepository, PostgresExecuteRepository postgresExecuteRepository) {
        super(postgresCallRepository, postgresQueryRepository, postgresExecuteRepository);
    }
}
