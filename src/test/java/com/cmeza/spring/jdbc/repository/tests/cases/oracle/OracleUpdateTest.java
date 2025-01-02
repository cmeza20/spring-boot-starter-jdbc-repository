package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.query.OracleQueryRepository;
import com.cmeza.spring.jdbc.repository.repositories.oracle.update.OracleUpdateRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
public class OracleUpdateTest extends AbstractUpdateTest {

    @Autowired
    public OracleUpdateTest(OracleUpdateRepository oracleUpdateRepository, OracleQueryRepository oracleQueryRepository) {
        super(oracleUpdateRepository, oracleQueryRepository);
    }

    @Override
    protected Class<?> classId() {
        return BigDecimal.class;
    }
}
