package com.cmeza.spring.jdbc.repository.repositories.sqlserver;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcFunction;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.FunctionContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = SqlServerInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface SqlServerFunctionRepository extends FunctionContract {

    @Override
    @JdbcFunction(name = "SQLSERVER_UnsupportedOperationException", outParameters = @Parameter(value = "result", type = Types.DOUBLE))
    Double functionSumWithOutParameter(Double numberOne, Double numberTwo);

    @Override
    @JdbcMapping(from = "numberOne", to = "@var_number_one", type = Types.DOUBLE)
    @JdbcMapping(from = "numberTwo", to = "@var_number_two", type = Types.DOUBLE)
    @JdbcFunction(name = "fn_multiplication_of_numbers_with_return")
    Double functionMultiplicationWithReturn(Double numberOne, Double numberTwo);

    @Override
    @JdbcFunction(name = "SQLSERVER_UnsupportedOperationException")
    List<Employee> functionEmployeesByGenderWithCursor(String gender);

    @Override
    @JdbcFunction(name = "SQLSERVER_UnsupportedOperationException", outParameters = {
            @Parameter(value = "out_first_name", type = Types.VARCHAR),
            @Parameter(value = "out_last_name", type = Types.VARCHAR)
    })
    Map<String, Object> functionEmployeeNamesWithOutParameters(Integer id);

    @Override
    @Transactional(transactionManager = SqlServerInitializer.JDBC_TRANSACTION_MANAGER)
    @JdbcMapping(from = "firstName", to = "@var_first_name", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "@var_last_name", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "@var_gender", type = Types.VARCHAR)
    @JdbcFunction(name = "SQLSERVER_UnsupportedOperationException")
    Optional<Employee> functionEmployeesByObjectWithCursor(Employee employee);
}
