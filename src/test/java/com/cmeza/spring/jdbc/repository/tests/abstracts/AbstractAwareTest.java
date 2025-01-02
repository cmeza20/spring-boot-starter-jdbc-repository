package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.aware.AbstractJdbcRepositoryAware;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.tests.contracts.AwareTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
public abstract class AbstractAwareTest extends AbstractException implements AwareTestContract {

    private final AbstractJdbcRepositoryAware jdbcRepositoryAware;

    protected abstract String qualifiedName();

    protected abstract String dialectName();

    @Test
    @Override
    public void testPropertiesResolver() {
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
        NamingStrategy namingStrategy = jdbcRepositoryAware.getNamingStrategy();
        AssertUtils.assertNotNull(namingStrategy);
    }
}
