package com.cmeza.spring.jdbc.repository.tests.cases.postgres;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.postgres.PostgresProcedureRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractProcedureTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.POSTGRES)
public class PostgresProcedureTest extends AbstractProcedureTest {

    private static final String UNSUPPORTED_OPERATION_MESSAGE = "@Procedure not supported for Postgres, use @Call";

    @Autowired
    public PostgresProcedureTest(PostgresProcedureRepository postgresProcedureRepository) {
        super(postgresProcedureRepository);
    }

    @Test
    @Override
    public void testProcedureEmployeeByIdWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testProcedureEmployeeByIdWithCursor,
                "PostgresProcedureTest::testProcedureEmployeeByIdWithCursor",
                UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Test
    @Override
    public void testProcedureEmployeesByGenderWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testProcedureEmployeesByGenderWithCursor,
                "PostgresProcedureTest::testProcedureEmployeesByGenderWithCursor",
                UNSUPPORTED_OPERATION_MESSAGE);
    }

    @Test
    @Override
    public void testProcedureEmployeeCountByGenderWithOutParameter() {
        testException(UnsupportedOperationException.class,
                super::testProcedureEmployeeCountByGenderWithOutParameter,
                "PostgresProcedureTest::testProcedureEmployeeCountByGenderWithOutParameter",
                UNSUPPORTED_OPERATION_MESSAGE);
    }
}
