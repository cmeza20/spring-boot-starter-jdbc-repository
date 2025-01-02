package com.cmeza.spring.jdbc.repository.tests.cases.oracle.raw;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.query.OracleQueryRepository;
import com.cmeza.spring.jdbc.repository.repositories.oracle.update.OracleRawUpdateRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractUpdateTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
public class OracleRawUpdateTest extends AbstractUpdateTest {

    @Autowired
    public OracleRawUpdateTest(OracleRawUpdateRepository oracleRawUpdateRepository, OracleQueryRepository oracleQueryRepository) {
        super(oracleRawUpdateRepository, oracleQueryRepository);
    }

    @Override
    protected Class<?> classId() {
        return BigDecimal.class;
    }
}
