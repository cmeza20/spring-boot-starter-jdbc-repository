package com.cmeza.spring.jdbc.repository.repositories.postgres.update;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.UpdateContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = PostgresInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = UpdateContract.DSL_RAW)
public interface PostgresRawUpdateDslRepository extends UpdateContract {

    @Override
    @JdbcRawUpdate.DSL
    int updateWithReturningInt(Employee employee);

    @Override
    @JdbcRawUpdate.DSL
    KeyHolder updateWithReturningHolder(Employee employee);

    @Override
    @JdbcRawUpdate.DSL
    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    @Override
    @JdbcRawUpdate.DSL
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
