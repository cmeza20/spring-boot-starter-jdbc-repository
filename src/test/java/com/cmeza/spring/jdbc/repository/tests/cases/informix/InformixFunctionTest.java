package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.InformixFunctionRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractFunctionTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixFunctionTest extends AbstractFunctionTest {

    private static final String UNSUPPORTED_OPERATION_MESSAGE = "@Function: Informix Dynamic Server is not one of the databases fully supported for function calls";

    @Autowired
    public InformixFunctionTest(InformixFunctionRepository informixFunctionRepository) {
        super(informixFunctionRepository);
    }

    @Test
    @Override
    public void testFunctionSumWithOutParameter() {
        testException(UnsupportedOperationException.class,
                super::testFunctionSumWithOutParameter,
                "InformixFunctionTest::testFunctionSumWithOutParameter",
                UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Test
    @Override
    public void testFunctionMultiplicationWithReturn() {
        testException(UnsupportedOperationException.class,
                super::testFunctionMultiplicationWithReturn,
                "InformixFunctionTest::testFunctionMultiplicationWithReturn",
                UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Test
    @Override
    public void testFunctionEmployeesByGenderWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByGenderWithCursor,
                "InformixFunctionTest::testFunctionEmployeesByGenderWithCursor",
                UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Test
    @Override
    public void testFunctionEmployeeNamesWithOutParameters() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeeNamesWithOutParameters,
                "InformixFunctionTest::testFunctionEmployeeNamesWithOutParameters",
                UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testFunctionEmployeesByObjectWithCursor,
                "InformixFunctionTest::testFunctionEmployeesByObjectWithCursor",
                UNSUPPORTED_OPERATION_MESSAGE);
    }
}
