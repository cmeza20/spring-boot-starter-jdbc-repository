package com.cmeza.spring.jdbc.repository.repositories.mysql;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcFunction;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.MysqlInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.FunctionContract;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = MysqlInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface MysqlFunctionRepository extends FunctionContract {

    @Override
    @JdbcFunction(name = "MYSQL_UnsupportedOperationException", outParameters = @Parameter(value = "res", type = Types.DOUBLE))
    Double functionSumWithOutParameter(Double numberOne, Double numberTwo);

    @Override
    @JdbcMapping(from = "numberOne", to = "var_number_one", type = Types.DOUBLE)
    @JdbcMapping(from = "numberTwo", to = "var_number_two", type = Types.DOUBLE)
    @JdbcFunction(name = "fn_multiplication_of_numbers_with_return")
    Double functionMultiplicationWithReturn(Double numberOne, Double numberTwo);

    @Override
    @JdbcFunction(name = "MYSQL_UnsupportedOperationException")
    List<Employee> functionEmployeesByGenderWithCursor(String gender);

    @Override
    @JdbcFunction(name = "MYSQL_UnsupportedOperationException")
    Map<String, Object> functionEmployeeNamesWithOutParameters(Integer id);

    @Override
    @JdbcFunction(name = "MYSQL_UnsupportedOperationException")
    Optional<Employee> functionEmployeesByObjectWithCursor(Employee employee);
}
