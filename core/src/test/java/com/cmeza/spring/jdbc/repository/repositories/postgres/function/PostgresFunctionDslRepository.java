package com.cmeza.spring.jdbc.repository.repositories.postgres.function;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.FunctionContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcFunction;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional(transactionManager = PostgresInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = FunctionContract.DSL)
public interface PostgresFunctionDslRepository extends FunctionContract {

    @Override
    @JdbcFunction.DSL
    Double functionSumWithOutParameter(Double numberOne, Double numberTwo);

    @Override
    @JdbcFunction.DSL
    Double functionMultiplicationWithReturn(Double numberOne, Double numberTwo);

    @Override
    @JdbcFunction.DSL
    List<Employee> functionEmployeesByGenderWithCursor(String gender);

    @Override

    @JdbcFunction.DSL
    Map<String, Object> functionEmployeeNamesWithOutParameters(Integer id);

    @Override
    @JdbcFunction.DSL
    Optional<Employee> functionEmployeesByObjectWithCursor(Employee employee);
}
