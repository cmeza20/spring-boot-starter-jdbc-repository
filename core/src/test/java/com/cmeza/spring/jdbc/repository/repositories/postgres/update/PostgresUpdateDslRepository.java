package com.cmeza.spring.jdbc.repository.repositories.postgres.update;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.UpdateContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcUpdate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = PostgresInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = UpdateContract.DSL)
public interface PostgresUpdateDslRepository extends UpdateContract {

    @Override
    @JdbcUpdate.DSL
    int updateWithReturningInt(Employee employee);

    @Override
    @JdbcUpdate.DSL
    KeyHolder updateWithReturningHolder(Employee employee);

    @Override
    @JdbcUpdate.DSL
    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    @Override
    @JdbcUpdate.DSL
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
