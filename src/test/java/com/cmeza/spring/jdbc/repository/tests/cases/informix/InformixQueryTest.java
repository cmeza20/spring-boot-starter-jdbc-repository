package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.query.InformixQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractQueryTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixQueryTest extends AbstractQueryTest {

    @Autowired
    public InformixQueryTest(InformixQueryRepository informixQueryRepository) {
        super(informixQueryRepository);
    }
}
