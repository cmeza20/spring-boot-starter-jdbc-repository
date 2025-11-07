package com.cmeza.spring.jdbc.repository.tests.cases.sqlserver;

import com.cmeza.spring.jdbc.repository.aware.SqlServerJdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractAwareTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.SQLSERVER)
public class SqlServerAwareTest extends AbstractAwareTest {

    @Autowired
    public SqlServerAwareTest(SqlServerJdbcRepositoryAware sqlServerJdbcRepositoryAware) {
        super(sqlServerJdbcRepositoryAware);
    }

    @Override
    protected String qualifiedName() {
        return SqlServerInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }

    @Override
    protected String dialectName() {
        return SqlServerInitializer.JDBC_DIALECT_NAME;
    }
}
