package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver.raw;

import com.cmeza.spring.jdbc.repository.configurations.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.pagination.SqlServerRawPaginationRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
@ContextConfiguration(initializers = SqlServerInitializer.class)
public class SqlServerRawPaginationTest extends AbstractPaginationTest {

    @Autowired
    public SqlServerRawPaginationTest(SqlServerRawPaginationRepository sqlServerRawPaginationRepository) {
        super(sqlServerRawPaginationRepository);
    }
}
