package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver;

import com.cmeza.spring.jdbc.repository.configurations.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.SqlServerCallRepository;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.SqlServerExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.query.SqlServerQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractCallTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
@ContextConfiguration(initializers = SqlServerInitializer.class)
public class SqlServerCallTest extends AbstractCallTest {

    @Autowired
    public SqlServerCallTest(SqlServerCallRepository sqlServerCallRepository, SqlServerQueryRepository sqlServerQueryRepository, SqlServerExecuteRepository sqlServerExecuteRepository) {
        super(sqlServerCallRepository, sqlServerQueryRepository, sqlServerExecuteRepository);
    }
}
