package com.cmeza.spring.jdbc.repository.tests.cases.informix;

import com.cmeza.spring.jdbc.repository.aware.InformixJdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.InformixInitializer;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractAwareTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.INFORMIX)
public class InformixAwareTest extends AbstractAwareTest {

    @Autowired
    public InformixAwareTest(InformixJdbcRepositoryAware informixJdbcRepositoryAware) {
        super(informixJdbcRepositoryAware);
    }

    @Override
    protected String qualifiedName() {
        return InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }

    @Override
    protected String dialectName() {
        return InformixInitializer.JDBC_DIALECT_NAME;
    }
}
