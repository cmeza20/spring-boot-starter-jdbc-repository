package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.query.SqlServerQueryRepository;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.update.SqlServerRawUpdateRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
public class SqlServerRawUpdateTest extends AbstractUpdateTest {

    @Autowired
    public SqlServerRawUpdateTest(SqlServerRawUpdateRepository sqlServerRawUpdateRepository, SqlServerQueryRepository sqlServerQueryRepository) {
        super(sqlServerRawUpdateRepository, sqlServerQueryRepository);
    }

    @Test
    @Override
    public void testUpdateWithReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateWithReturningHolder,
                "SqlServerRawUpdateTest::testUpdateWithReturningHolder",
                "KeyHolder not supported for SqlServer Update");
    }

    @Test
    @Override
    public void testUpdateComplexReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexReturningHolder,
                "SqlServerRawUpdateTest::testUpdateComplexReturningHolder",
                "KeyHolder not supported for SqlServer Update");
    }

    @Test
    @Override
    public void testUpdateComplexTwoReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexTwoReturningHolder,
                "SqlServerRawUpdateTest::testUpdateComplexTwoReturningHolder",
                "KeyHolder not supported for SqlServer Update");
    }
}
