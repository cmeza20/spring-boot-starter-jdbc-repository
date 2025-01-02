package com.cmeza.spring.jdbc.repository.tests.cases.mysql;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.update.MysqlUpdateRepository;
import com.cmeza.spring.jdbc.repository.repositories.mysql.query.MysqlQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
public class MysqlUpdateTest extends AbstractUpdateTest {

    @Autowired
    public MysqlUpdateTest(MysqlUpdateRepository mysqlUpdateRepository, MysqlQueryRepository mysqlQueryRepository) {
        super(mysqlUpdateRepository, mysqlQueryRepository);
    }

    @Test
    @Override
    public void testUpdateWithReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateWithReturningHolder,
                "MysqlUpdateTest::testUpdateWithReturningHolder",
                "KeyHolder not supported for MySQL Update");
    }

    @Test
    @Override
    public void testUpdateComplexReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexReturningHolder,
                "MysqlUpdateTest::testUpdateComplexReturningHolder",
                "KeyHolder not supported for MySQL Update");
    }

    @Test
    @Override
    public void testUpdateComplexTwoReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexTwoReturningHolder,
                "MysqlUpdateTest::testUpdateComplexTwoReturningHolder",
                "KeyHolder not supported for MySQL Update");
    }
}
