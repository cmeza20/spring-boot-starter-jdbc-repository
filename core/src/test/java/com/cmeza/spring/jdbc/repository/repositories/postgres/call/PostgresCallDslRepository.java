package com.cmeza.spring.jdbc.repository.repositories.postgres.call;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.CallContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcCall;
import org.springframework.transaction.annotation.Transactional;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = CallContract.DSL)
public interface PostgresCallDslRepository extends CallContract {

    @Override
    @JdbcCall.DSL
    @Transactional(transactionManager = PostgresInitializer.JDBC_TRANSACTION_MANAGER)
    void callDepartmentCreate(Department department);
}
