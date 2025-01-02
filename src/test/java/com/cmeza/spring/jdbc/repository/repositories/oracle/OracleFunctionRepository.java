package com.cmeza.spring.jdbc.repository.repositories.oracle;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcFunction;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.OracleInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.FunctionContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional(transactionManager = OracleInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = OracleInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface OracleFunctionRepository extends FunctionContract {

    @Override
    @JdbcFunction(name = "ORACLE_UnsupportedOperationException", outParameters = @Parameter(value = "result", type = Types.DOUBLE))
    Double functionSumWithOutParameter(Double numberOne, Double numberTwo);

    @Override
    @JdbcMapping(from = "numberOne", to = "var_number_one", type = Types.DOUBLE)
    @JdbcMapping(from = "numberTwo", to = "var_number_two", type = Types.DOUBLE)
    @JdbcFunction(name = "fn_multiplication_of_numbers_with_return")
    Double functionMultiplicationWithReturn(Double numberOne, Double numberTwo);

    @Override
    @JdbcFunction(name = "ORACLE_UnsupportedOperationException")
    List<Employee> functionEmployeesByGenderWithCursor(String gender);

    @Override
    @JdbcFunction(name = "ORACLE_UnsupportedOperationException")
    Map<String, Object> functionEmployeeNamesWithOutParameters(Integer id);

    @Override
    @JdbcFunction(name = "ORACLE_UnsupportedOperationException")
    Optional<Employee> functionEmployeesByObjectWithCursor(Employee employee);
}
