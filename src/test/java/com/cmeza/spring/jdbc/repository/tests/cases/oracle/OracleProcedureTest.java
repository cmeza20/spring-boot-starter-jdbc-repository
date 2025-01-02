package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.OracleProcedureRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractProcedureTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
public class OracleProcedureTest extends AbstractProcedureTest {

    @Autowired
    public OracleProcedureTest(OracleProcedureRepository oracleProcedureRepository) {
        super(oracleProcedureRepository);
    }

    @Test
    @Override
    public void testProcedureEmployeesByGenderWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testProcedureEmployeesByGenderWithCursor,
                "OracleProcedureTest::testProcedureEmployeesByGenderWithCursor",
                "Return List not supported for Oracle Procedure, use JdbcRawQuery");
    }

    @Test
    @Override
    public void testProcedureEmployeeByIdWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testProcedureEmployeeByIdWithCursor,
                "OracleProcedureTest::testProcedureEmployeeByIdWithCursor",
                "Optional not supported for Oracle Procedure, use JdbcRawQuery");
    }
}
