package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.query.SqlServerRawQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractQueryTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
public class SqlServerRawQueryTest extends AbstractQueryTest {

    @Autowired
    public SqlServerRawQueryTest(SqlServerRawQueryRepository sqlServerRawQueryRepository) {
        super(sqlServerRawQueryRepository);
    }
}
