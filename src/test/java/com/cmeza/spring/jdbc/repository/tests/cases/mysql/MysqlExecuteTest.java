package com.cmeza.spring.jdbc.repository.tests.cases.mysql;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.MysqlExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.mysql.query.MysqlQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractExecuteTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
public class MysqlExecuteTest extends AbstractExecuteTest {

    @Autowired
    public MysqlExecuteTest(MysqlExecuteRepository mysqlExecuteRepository, MysqlQueryRepository mysqlQueryRepository) {
        super(mysqlExecuteRepository, mysqlQueryRepository);
    }
}
