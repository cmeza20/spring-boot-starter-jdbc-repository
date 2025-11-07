package com.cmeza.spring.jdbc.repository.repositories.postgres.execute;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;
import org.springframework.transaction.annotation.Transactional;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = ExecuteContract.DSL)
public interface PostgresExecuteDslRepository extends ExecuteContract {

    @Override
    @JdbcExecute.DSL
    int deleteDepartmentWithReturningInt(@JdbcParam("id") String id);

    @Override
    @JdbcExecute.DSL
    int deleteEmployeeWithReturningInt(Integer id);

    @Override
    @JdbcExecute.DSL
    int deleteEmployeesWithArrayAndReturningInt(String... names);

    @Override
    @Transactional(transactionManager = PostgresInitializer.JDBC_TRANSACTION_MANAGER)
    @JdbcExecute.DSL
    void executeCallDepartmentCreateWithoutResult(Department department);
}
