package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.execute.PostgresExecuteDslRepository;
import com.cmeza.spring.jdbc.repository.repositories.postgres.query.PostgresQueryDslRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractExecuteTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
@ContextConfiguration(initializers = PostgresInitializer.class)
public class PostgresExecuteDslTest extends AbstractExecuteTest {

    @Autowired
    public PostgresExecuteDslTest(PostgresExecuteDslRepository postgresExecuteDslRepository, PostgresQueryDslRepository postgresQueryDslRepository) {
        super(postgresExecuteDslRepository, postgresQueryDslRepository);
    }
}
