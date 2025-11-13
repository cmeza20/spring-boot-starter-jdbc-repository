package com.cmeza.spring.jdbc.repository.repositories.informix;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcFunction;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.InformixInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.FunctionContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional(transactionManager = InformixInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface InformixFunctionRepository extends FunctionContract {

    @Override
    @JdbcMapping(from = "numberOne", to = "var_number_one", type = Types.DOUBLE)
    @JdbcMapping(from = "numberTwo", to = "var_number_two", type = Types.DOUBLE)
    @JdbcFunction(name = "fn_sum_of_numbers_with_out_parameter", outParameters = @Parameter(value = "result", type = Types.DOUBLE))
    Double functionSumWithOutParameter(Double numberOne, Double numberTwo);

    @Override
    @JdbcMapping(from = "numberOne", to = "var_number_one", type = Types.DOUBLE)
    @JdbcMapping(from = "numberTwo", to = "var_number_two", type = Types.DOUBLE)
    @JdbcFunction(name = "fn_multiplication_of_numbers_with_return")
    Double functionMultiplicationWithReturn(Double numberOne, Double numberTwo);

    @Override
    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcFunction(name = "fn_employees_by_gender")
    List<Employee> functionEmployeesByGenderWithCursor(String gender);

    @Override
    @JdbcMapping(from = "id", to = "var_id", type = Types.INTEGER)
    @JdbcFunction(name = "fn_employee_names_by_id_with_out_parameters", outParameters = {
            @Parameter(value = "out_first_name", type = Types.VARCHAR),
            @Parameter(value = "out_last_name", type = Types.VARCHAR)
    })
    Map<String, Object> functionEmployeeNamesWithOutParameters(Integer id);

    @Override
    @JdbcMapping(from = "firstName", to = "var_first_name", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "var_last_name", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcFunction(name = "fn_employees_by_object")
    Optional<Employee> functionEmployeesByObjectWithCursor(Employee employee);
}
