package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.query.InformixQueryRepository;
import com.cmeza.spring.jdbc.repository.repositories.informix.update.InformixUpdateRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixUpdateTest extends AbstractUpdateTest {

    @Autowired
    public InformixUpdateTest(InformixUpdateRepository informixUpdateRepository, InformixQueryRepository informixQueryRepository) {
        super(informixUpdateRepository, informixQueryRepository);
    }

    @Test
    @Override
    public void testUpdateWithReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateWithReturningHolder,
                "InformixUpdateTest::testUpdateWithReturningHolder",
                "KeyHolder not supported for Informix Update");
    }

    @Test
    @Override
    public void testUpdateComplexReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexReturningHolder,
                "InformixUpdateTest::testUpdateComplexReturningHolder",
                "KeyHolder not supported for Informix Update");
    }

    @Test
    @Override
    public void testUpdateComplexTwoReturningHolder() {
        testException(UnsupportedOperationException.class,
                super::testUpdateComplexTwoReturningHolder,
                "InformixUpdateTest::testUpdateComplexTwoReturningHolder",
                "KeyHolder not supported for Informix Update");
    }
}
