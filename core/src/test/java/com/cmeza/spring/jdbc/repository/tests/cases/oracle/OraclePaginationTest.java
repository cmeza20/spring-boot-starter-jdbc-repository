package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.pagination.OraclePaginationRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
@ContextConfiguration(initializers = OracleInitializer.class)
public class OraclePaginationTest extends AbstractPaginationTest {

    @Autowired
    public OraclePaginationTest(OraclePaginationRepository oraclePaginationRepository) {
        super(oraclePaginationRepository);
    }
}
