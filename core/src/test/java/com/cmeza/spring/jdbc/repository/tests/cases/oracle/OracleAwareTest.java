package com.cmeza.spring.jdbc.repository.tests.cases.oracle;

import com.cmeza.spring.jdbc.repository.aware.OracleJdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.OracleInitializer;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractAwareTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.ORACLE)
public class OracleAwareTest extends AbstractAwareTest {

    @Autowired
    public OracleAwareTest(OracleJdbcRepositoryAware oracleJdbcRepositoryAware) {
        super(oracleJdbcRepositoryAware);
    }

    @Override
    protected String qualifiedName() {
        return OracleInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }

    @Override
    protected String dialectName() {
        return OracleInitializer.JDBC_DIALECT_NAME;
    }
}
