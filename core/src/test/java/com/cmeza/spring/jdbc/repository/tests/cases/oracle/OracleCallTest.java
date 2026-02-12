package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.OracleCallRepository;
import com.cmeza.spring.jdbc.repository.repositories.oracle.OracleExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.oracle.query.OracleQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractCallTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
@ContextConfiguration(initializers = OracleInitializer.class)
public class OracleCallTest extends AbstractCallTest {

    @Autowired
    public OracleCallTest(OracleCallRepository oracleCallRepository, OracleQueryRepository oracleQueryRepository, OracleExecuteRepository oracleExecuteRepository) {
        super(oracleCallRepository, oracleQueryRepository, oracleExecuteRepository);
    }
}
