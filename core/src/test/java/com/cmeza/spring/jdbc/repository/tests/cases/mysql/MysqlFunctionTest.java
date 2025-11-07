package com.cmeza.spring.jdbc.repository.tests.cases.mysql;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.mysql.MysqlFunctionRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractFunctionTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
public class MysqlFunctionTest extends AbstractFunctionTest {

    @Autowired
    public MysqlFunctionTest(MysqlFunctionRepository mysqlFunctionRepository) {
        super(mysqlFunctionRepository);
    }

    @Test
    @Override
    public void testFunctionSumWithOutParameter() {
        testException(UnsupportedOperationException.class,
                super::testFunctionSumWithOutParameter,
                "MysqlFunctionTest::testFunctionSumWithOutParameter",
                "OUT parameter not supported for MySQL Function, use JdbcProcedure");
    }

    @Test
    @Override
    public void testFunctionEmployeesByGenderWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByGenderWithCursor,
                "MysqlFunctionTest::testFunctionEmployeesByGenderWithCursor",
                "Return List not supported for MySQL Function, use JdbcProcedure");
    }

    @Test
    @Override
    public void testFunctionEmployeeNamesWithOutParameters() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeeNamesWithOutParameters,
                "MysqlFunctionTest::testFunctionEmployeeNamesWithOutParameters",
                "Return Map not supported for MySQL Function, use JdbcProcedure");
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByObjectWithCursor,
                "MysqlFunctionTest::testFunctionEmployeesByObjectWithCursor",
                "Optional not supported for MySQL Function, use JdbcProcedure");
    }
}
