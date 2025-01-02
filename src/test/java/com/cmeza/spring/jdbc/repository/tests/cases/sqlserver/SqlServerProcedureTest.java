package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.SqlServerProcedureRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractProcedureTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
public class SqlServerProcedureTest extends AbstractProcedureTest {

    @Autowired
    public SqlServerProcedureTest(SqlServerProcedureRepository sqlServerProcedureRepository) {
        super(sqlServerProcedureRepository);
    }
}
