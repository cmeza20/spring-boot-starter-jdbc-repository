package com.cmeza.spring.jdbc.repository.tests.cases.informix.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.pagination.InformixRawPaginationRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixRawPaginationTest extends AbstractPaginationTest {

    @Autowired
    public InformixRawPaginationTest(InformixRawPaginationRepository informixRawPaginationRepository) {
        super(informixRawPaginationRepository);
    }
}
