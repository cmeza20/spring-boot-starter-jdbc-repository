package com.cmeza.spring.jdbc.repository.repositories.postgres.query;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.models.DepartmentEmployee;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndSalaryProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndTitleProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawQuery;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;

import java.util.*;
import java.util.stream.Stream;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = QueryContract.DSL_RAW)
public interface PostgresRawQueryDslRepository extends QueryContract {

    @Override
    @JdbcRawQuery.DSL
    List<Employee> getAllEmployeeHardcoded();

    @Override
    @JdbcRawQuery.DSL
    Set<Employee> getAllEmployeeSetFromProperties();

    @Override
    @JdbcRawQuery.DSL
    Employee[] getAllEmployeeArrayFromYml();

    @Override
    @JdbcRawQuery.DSL
    Stream<Employee> getAllEmployeeStreamFromFile();

    @Override
    @JdbcRawQuery.DSL
    List<Employee> getAllEmployeeListWithCondition(Integer id);

    @Override
    @JdbcRawQuery.DSL
    Employee getOneEmployeeWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcRawQuery.DSL
    Optional<Employee> getEmployeeOptionalWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcRawQuery.DSL
    Employee getOneEmployeeWithConditionMapping(String conditionOne, String conditionTwo);

    @Override
    @JdbcRawQuery.DSL
    Employee getOneEmployeeWithConditionAndRowMapper(Integer id);

    @Override
    @JdbcRawQuery.DSL
    Employee getOneEmployeeWithConditionAndComplexRowMapper(Integer id);

    @Override
    @JdbcRawQuery.DSL
    Employee getOneEmployeeWithObjectCondition(DepartmentEmployee departmentEmployee);

    @Override
    @JdbcRawQuery.DSL
    EmployeeProjection getOneEmployeeWithConditionWithProjection(Integer id);

    @Override
    @JdbcRawQuery.DSL
    EmployeeAndSalaryProjection getOneEmployeeWithConditionWithComplexProjection(Integer id);

    @Override
    @JdbcRawQuery.DSL
    EmployeeAndTitleProjection getOneEmployeeWithConditionWithComplexProjectionImpl(Integer id);

    @Override
    @JdbcRawQuery.DSL
    Date getBirthdateEmployeeWithCondition(Integer id);

    @Override
    @JdbcRawQuery.DSL
    Map<String, Object> getEmployeeMapWithCondition(Integer id);

    @Override
    @JdbcRawQuery.DSL
    List<Employee> getAllEmployeeWithConditionArray(Integer... ids);

    @Override
    @JdbcRawQuery.DSL
    List<Employee> getAllEmployeeWithConditionArrayAndMapping(Integer... ids);

    @Override
    @JdbcRawQuery.DSL
    List<Employee> getAllEmployeeWithConditionListAndMapping(List<Integer> ids);

    @Override
    @JdbcRawQuery.DSL
    Optional<Department> getDepartmentOptionalWithCondition(String id);
}
