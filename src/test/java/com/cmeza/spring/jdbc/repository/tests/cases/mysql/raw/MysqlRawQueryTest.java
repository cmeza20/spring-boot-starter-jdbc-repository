package com.cmeza.spring.jdbc.repository.tests.cases.mysql.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.query.MysqlRawQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractQueryTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
public class MysqlRawQueryTest extends AbstractQueryTest {

    @Autowired
    public MysqlRawQueryTest(MysqlRawQueryRepository mysqlRawQueryRepository) {
        super(mysqlRawQueryRepository);
    }
}
