package com.cmeza.spring.jdbc.repository.tests.cases.oracle.raw;

import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.oracle.pagination.OracleRawPaginationRepository;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractPaginationTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
@ContextConfiguration(initializers = OracleInitializer.class)
public class OracleRawPaginationTest extends AbstractPaginationTest {

    @Autowired
    public OracleRawPaginationTest(OracleRawPaginationRepository oracleRawPaginationRepository) {
        super(oracleRawPaginationRepository);
    }
}
