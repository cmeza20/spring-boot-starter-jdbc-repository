package com.cmeza.spring.jdbc.repository.tests.cases.postgres.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.pagination.PostgresRawPaginationDslRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresRawPaginationDslTest extends AbstractPaginationTest {

    @Autowired
    public PostgresRawPaginationDslTest(PostgresRawPaginationDslRepository postgresRawPaginationDslRepository) {
        super(postgresRawPaginationDslRepository);
    }
}
