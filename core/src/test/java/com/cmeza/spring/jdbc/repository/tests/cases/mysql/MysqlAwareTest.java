package com.cmeza.spring.jdbc.repository.tests.cases.mysql;

import com.cmeza.spring.jdbc.repository.aware.MysqlJdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.MysqlInitializer;
import com.cmeza.spring.jdbc.repository.tests.abstracts.AbstractAwareTest;
import com.cmeza.spring.jdbc.repository.tests.utils.EnabledIfProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfProfile(profiles = TestConstants.MYSQL)
public class MysqlAwareTest extends AbstractAwareTest {

    @Autowired
    public MysqlAwareTest(MysqlJdbcRepositoryAware mysqlJdbcRepositoryAware) {
        super(mysqlJdbcRepositoryAware);
    }

    @Override
    protected String qualifiedName() {
        return MysqlInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN;
    }

    @Override
    protected String dialectName() {
        return MysqlInitializer.JDBC_DIALECT_NAME;
    }
}
