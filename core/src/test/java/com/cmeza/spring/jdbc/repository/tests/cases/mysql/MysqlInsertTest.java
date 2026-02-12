package com.cmeza.spring.jdbc.repository.tests.cases.mysql;

import com.cmeza.spring.jdbc.repository.configurations.MysqlInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.MysqlExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.mysql.MysqlInsertRepository;
import com.cmeza.spring.jdbc.repository.repositories.mysql.query.MysqlQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractInsertTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
@ContextConfiguration(initializers = MysqlInitializer.class)
public class MysqlInsertTest extends AbstractInsertTest {

    @Autowired
    public MysqlInsertTest(MysqlInsertRepository mysqlInsertRepository, MysqlQueryRepository mysqlQueryRepository, MysqlExecuteRepository mysqlExecuteRepository) {
        super(mysqlInsertRepository, mysqlQueryRepository, mysqlExecuteRepository);
    }
}
