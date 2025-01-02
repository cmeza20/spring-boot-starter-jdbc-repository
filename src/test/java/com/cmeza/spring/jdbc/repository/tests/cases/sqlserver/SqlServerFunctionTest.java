package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.sqlserver.SqlServerFunctionRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractFunctionTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
public class SqlServerFunctionTest extends AbstractFunctionTest {

    @Autowired
    public SqlServerFunctionTest(SqlServerFunctionRepository sqlServerFunctionRepository) {
        super(sqlServerFunctionRepository);
    }

    @Test
    @Override
    public void testFunctionSumWithOutParameter() {
        testException(UnsupportedOperationException.class,
                super::testFunctionSumWithOutParameter,
                "SqlServerFunctionTest::testFunctionSumWithOutParameter",
                "OUT parameter not supported for SqlServer Function, use JdbcProcedure");
    }

    @Test
    @Override
    public void testFunctionEmployeeNamesWithOutParameters() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeeNamesWithOutParameters,
                "SqlServerFunctionTest::testFunctionEmployeeNamesWithOutParameters",
                "OUT parameter not supported for SqlServer Function, use JdbcProcedure");
    }

    @Test
    @Override
    public void testFunctionEmployeesByGenderWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByGenderWithCursor,
                "SqlServerFunctionTest::testFunctionEmployeesByGenderWithCursor",
                "Return List not supported for SqlServer Function, use JdbcProcedure");
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByObjectWithCursor,
                "SqlServerFunctionTest::testFunctionEmployeesByObjectWithCursor",
                "Optional not supported for SqlServer Function, use JdbcProcedure");
    }
}
