package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.SqlServerExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.SqlServerInsertRepository;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.query.SqlServerQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractInsertTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
public class SqlServerInsertTest extends AbstractInsertTest {

    @Autowired
    public SqlServerInsertTest(SqlServerInsertRepository sqlServerInsertRepository, SqlServerQueryRepository sqlServerQueryRepository, SqlServerExecuteRepository sqlServerExecuteRepository) {
        super(sqlServerInsertRepository, sqlServerQueryRepository, sqlServerExecuteRepository);
    }
}
