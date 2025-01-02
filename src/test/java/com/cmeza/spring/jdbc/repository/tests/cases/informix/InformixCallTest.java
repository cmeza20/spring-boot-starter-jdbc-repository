package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.informix.InformixCallRepository;
import com.cmeza.spring.jdbc.repository.repositories.informix.InformixExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.informix.query.InformixQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractCallTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixCallTest extends AbstractCallTest {

    @Autowired
    public InformixCallTest(InformixCallRepository informixCallRepository, InformixQueryRepository informixQueryRepository, InformixExecuteRepository informixExecuteRepository) {
        super(informixCallRepository, informixQueryRepository, informixExecuteRepository);
    }
}
