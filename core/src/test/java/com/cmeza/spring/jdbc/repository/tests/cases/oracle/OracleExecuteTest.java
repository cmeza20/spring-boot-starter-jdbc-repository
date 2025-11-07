package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.OracleExecuteRepository;
import com.cmeza.spring.jdbc.repository.repositories.oracle.query.OracleQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractExecuteTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
public class OracleExecuteTest extends AbstractExecuteTest {

    @Autowired
    public OracleExecuteTest(OracleExecuteRepository oracleExecuteRepository, OracleQueryRepository oracleQueryRepository) {
        super(oracleExecuteRepository, oracleQueryRepository);
    }
}
