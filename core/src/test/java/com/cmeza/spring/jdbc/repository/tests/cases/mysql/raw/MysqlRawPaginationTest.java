package com.cmeza.spring.jdbc.repository.tests.cases.mysql.raw;

import com.cmeza.spring.jdbc.repository.configurations.MysqlInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.pagination.MysqlRawPaginationRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
@ContextConfiguration(initializers = MysqlInitializer.class)
public class MysqlRawPaginationTest extends AbstractPaginationTest {

    @Autowired
    public MysqlRawPaginationTest(MysqlRawPaginationRepository mysqlRawPaginationRepository) {
        super(mysqlRawPaginationRepository);
    }
}
