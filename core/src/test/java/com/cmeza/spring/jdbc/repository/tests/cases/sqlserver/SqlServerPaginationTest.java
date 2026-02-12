package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver;

import com.cmeza.spring.jdbc.repository.configurations.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.pagination.SqlServerPaginationRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
@ContextConfiguration(initializers = SqlServerInitializer.class)
public class SqlServerPaginationTest extends AbstractPaginationTest {

    @Autowired
    public SqlServerPaginationTest(SqlServerPaginationRepository sqlServerPaginationRepository) {
        super(sqlServerPaginationRepository);
    }
}
