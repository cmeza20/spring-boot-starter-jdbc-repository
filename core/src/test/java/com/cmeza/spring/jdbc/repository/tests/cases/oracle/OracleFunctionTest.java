package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.OracleFunctionRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractFunctionTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
@ContextConfiguration(initializers = OracleInitializer.class)
public class OracleFunctionTest extends AbstractFunctionTest {

    @Autowired
    public OracleFunctionTest(OracleFunctionRepository oracleFunctionRepository) {
        super(oracleFunctionRepository);
    }

    @Test
    @Override
    public void testFunctionSumWithOutParameter() {
        testException(UnsupportedOperationException.class,
                super::testFunctionSumWithOutParameter,
                "OracleFunctionTest::testFunctionSumWithOutParameter",
                "OUT parameter not supported for Oracle Function, use JdbcProcedure");
    }

    @Test
    @Override
    public void testFunctionEmployeesByGenderWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByGenderWithCursor,
                "OracleFunctionTest::testFunctionEmployeesByGenderWithCursor",
                "Return List not supported for Oracle Function, use JdbcRawQuery");
    }

    @Test
    @Override
    public void testFunctionEmployeeNamesWithOutParameters() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeeNamesWithOutParameters,
                "OracleFunctionTest::testFunctionEmployeeNamesWithOutParameters",
                "Return Map not supported for Oracle Function, use JdbcRawQuery");
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByObjectWithCursor,
                "OracleFunctionTest::testFunctionEmployeesByObjectWithCursor",
                "Optional not supported for Oracle Function, use JdbcRawQuery");
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursorAndClassAttributes() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByObjectWithCursorAndClassAttributes,
                "OracleFunctionTest::testFunctionEmployeesByObjectWithCursorAndClassAttributes",
                "Optional not supported for Oracle Function, use JdbcRawQuery");
    }
}
