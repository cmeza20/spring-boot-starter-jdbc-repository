package com.cmeza.spring.jdbc.repository.tests.cases.informix.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.query.InformixQueryRepository;
import com.cmeza.spring.jdbc.repository.repositories.informix.update.InformixRawUpdateRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixRawUpdateTest extends AbstractUpdateTest {

    @Autowired
    public InformixRawUpdateTest(InformixRawUpdateRepository informixRawUpdateRepository, InformixQueryRepository informixQueryRepository) {
        super(informixRawUpdateRepository, informixQueryRepository);
    }

    @Test
    @Override
    public void testUpdateWithReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateWithReturningHolder,
                "InformixRawUpdateTest::testUpdateWithReturningHolder",
                "KeyHolder not supported for Informix Update");
    }

    @Test
    @Override
    public void testUpdateComplexReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexReturningHolder,
                "InformixRawUpdateTest::testUpdateComplexReturningHolder",
                "KeyHolder not supported for Informix Update");
    }

    @Test
    @Override
    public void testUpdateComplexTwoReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexTwoReturningHolder,
                "InformixRawUpdateTest::testUpdateComplexTwoReturningHolder",
                "KeyHolder not supported for Informix Update");
    }
}
