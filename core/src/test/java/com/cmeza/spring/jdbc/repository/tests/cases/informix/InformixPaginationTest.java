package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.configurations.InformixInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.pagination.InformixPaginationRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
@ContextConfiguration(initializers = InformixInitializer.class)
public class InformixPaginationTest extends AbstractPaginationTest {

    @Autowired
    public InformixPaginationTest(InformixPaginationRepository informixPaginationRepository) {
        super(informixPaginationRepository);
    }
}
