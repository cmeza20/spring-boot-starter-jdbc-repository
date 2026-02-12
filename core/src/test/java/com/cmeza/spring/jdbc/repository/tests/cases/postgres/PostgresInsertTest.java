package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.execute.PostgresExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.insert.PostgresInsertRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractInsertTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class PostgresInsertTest extends AbstractInsertTest {

    @Autowired
    public PostgresInsertTest(PostgresInsertRepository postgresInsertRepository, PostgresQueryRepository postgresQueryRepository, PostgresExecuteRepository postgresExecuteRepository) {
        super(postgresInsertRepository, postgresQueryRepository, postgresExecuteRepository);
    }
}
