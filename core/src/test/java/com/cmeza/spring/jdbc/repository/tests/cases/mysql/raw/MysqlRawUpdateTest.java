package com.cmeza.spring.jdbc.repository.tests.cases.mysql.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.query.MysqlQueryRepository;
import com.cmeza.spring.jdbc.repository.repositories.mysql.update.MysqlRawUpdateRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
public class MysqlRawUpdateTest extends AbstractUpdateTest {

    @Autowired
    public MysqlRawUpdateTest(MysqlRawUpdateRepository mysqlRawUpdateRepository, MysqlQueryRepository mysqlQueryRepository) {
        super(mysqlRawUpdateRepository, mysqlQueryRepository);
    }

    @Test
    @Override
    public void testUpdateWithReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateWithReturningHolder,
                "MysqlRawUpdateTest::testUpdateWithReturningHolder",
                "KeyHolder not supported for MySQL Update");
    }

    @Test
    @Override
    public void testUpdateComplexReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexReturningHolder,
                "MysqlRawUpdateTest::testUpdateComplexReturningHolder",
                "KeyHolder not supported for MySQL Update");
    }

    @Test
    @Override
    public void testUpdateComplexTwoReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexTwoReturningHolder,
                "MysqlRawUpdateTest::testUpdateComplexTwoReturningHolder",
                "KeyHolder not supported for MySQL Update");
    }
}
