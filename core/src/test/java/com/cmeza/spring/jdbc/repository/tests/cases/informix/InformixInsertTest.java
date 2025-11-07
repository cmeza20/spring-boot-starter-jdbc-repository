package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.InformixExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.informix.InformixInsertRepository;
import com.cmeza.spring.jdbc.repository.repositories.informix.query.InformixQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractInsertTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixInsertTest extends AbstractInsertTest {

    @Autowired
    public InformixInsertTest(InformixInsertRepository informixInsertRepository, InformixQueryRepository informixQueryRepository, InformixExecuteRepository informixExecuteRepository) {
        super(informixInsertRepository, informixQueryRepository, informixExecuteRepository);
    }
}
