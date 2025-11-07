package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.InformixProcedureRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractProcedureTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixProcedureTest extends AbstractProcedureTest {


    @Autowired
    public InformixProcedureTest(InformixProcedureRepository informixProcedureRepository) {
        super(informixProcedureRepository);
    }

    @Test
    @Override
    public void testProcedureEmployeeByIdWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testProcedureEmployeeByIdWithCursor,
                "InformixProcedureTest::testProcedureEmployeeByIdWithCursor",
                "Optional not supported for Informix Procedure");
    }

    @Test
    @Override
    public void testProcedureEmployeesByGenderWithCursor() {
        testException(UnsupportedOperationException.class,
                super::testProcedureEmployeesByGenderWithCursor,
                "InformixProcedureTest::testProcedureEmployeesByGenderWithCursor",
                "Return List not supported for Informix Procedure");
    }
}
