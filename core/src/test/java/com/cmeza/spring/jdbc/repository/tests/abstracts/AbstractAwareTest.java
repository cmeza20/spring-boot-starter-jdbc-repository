package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.aware.AbstractJdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.tests.contracts.AwareTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractAwareTest extends AbstractException implements AwareTestContract {

    private final AbstractJdbcRepositoryAware jdbcRepositoryAware;

    protected abstract String qualifiedName();

    protected abstract String dialectName();

    @BeforeAll
    static void setup() {
        log.info("Setup AwareTest");
    }

    @Test
    @Override
    public void testPropertiesResolver() {
        log.info("Init testPropertiesResolver");

        JdbcPropertyResolver propertiesResolver = jdbcRepositoryAware.getJdbcPropertyResolver();
        AssertUtils.assertObject(propertiesResolver, JdbcPropertyResolver.class);

        String jdbcEmployee = propertiesResolver.resolvePlaceholders("file:/jdbc-employee.sql");
        AssertUtils.assertObject(jdbcEmployee, String.class);

        String table = propertiesResolver.resolvePlaceholders("${properties.employee.query.table}");
        AssertUtils.assertEquals(table, "employee", String.class);

        testException(IllegalArgumentException.class, () -> propertiesResolver.resolveRequiredPlaceholders("${required.not.found}"), "AbstractAwareTest::testPropertiesResolver", "Could not resolve placeholder 'required.not.found' in value \"${required.not.found}\"");
    }

    @Test
    @Override
    public void testJdbcRepositoryTemplate() {
        log.info("Init testJdbcRepositoryTemplate");

        JdbcRepositoryTemplate jdbcRepositoryTemplateQualified = jdbcRepositoryAware.getJdbcRepositoryTemplate();
        AssertUtils.assertNotNull(jdbcRepositoryTemplateQualified);

        String repositoryBeanName = jdbcRepositoryTemplateQualified.getRepositoryBeanName();
        AssertUtils.assertEquals(repositoryBeanName, qualifiedName(), String.class);

        String dialectName = jdbcRepositoryTemplateQualified.getMetadata().getDatabaseProductName();
        AssertUtils.assertEquals(dialectName, dialectName(), String.class);
    }

    @Test
    @Override
    public void testNamingStrategy() {
        log.info("Init testNamingStrategy");

        NamingStrategy namingStrategy = jdbcRepositoryAware.getNamingStrategy();
        AssertUtils.assertNotNull(namingStrategy);
    }
}
