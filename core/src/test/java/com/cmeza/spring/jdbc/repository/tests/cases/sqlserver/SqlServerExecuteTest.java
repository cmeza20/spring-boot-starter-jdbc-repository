package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.SqlServerExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.query.SqlServerQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractExecuteTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
public class SqlServerExecuteTest extends AbstractExecuteTest {

    @Autowired
    public SqlServerExecuteTest(SqlServerExecuteRepository sqlServerExecuteRepository, SqlServerQueryRepository sqlServerQueryRepository) {
        super(sqlServerExecuteRepository, sqlServerQueryRepository);
    }
}
