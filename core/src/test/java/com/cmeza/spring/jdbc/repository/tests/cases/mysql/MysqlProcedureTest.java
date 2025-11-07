package com.cmeza.spring.jdbc.repository.tests.cases.mysql;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.MysqlProcedureRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractProcedureTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
public class MysqlProcedureTest extends AbstractProcedureTest {

    @Autowired
    public MysqlProcedureTest(MysqlProcedureRepository mysqlProcedureRepository) {
        super(mysqlProcedureRepository);
    }
}
