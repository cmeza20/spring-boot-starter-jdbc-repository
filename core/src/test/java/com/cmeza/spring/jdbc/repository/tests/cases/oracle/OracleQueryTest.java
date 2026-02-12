package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.query.OracleQueryRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractQueryTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
@ContextConfiguration(initializers = OracleInitializer.class)
public class OracleQueryTest extends AbstractQueryTest {

    @Autowired
    public OracleQueryTest(OracleQueryRepository oracleQueryRepository) {
        super(oracleQueryRepository);
    }

    @Override
    protected Class<?> classId() {
        return BigDecimal.class;
    }
}
