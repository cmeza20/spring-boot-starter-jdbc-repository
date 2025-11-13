package com.cmeza.spring.jdbc.repository.repositories.postgres.query;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.models.DepartmentEmployee;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndSalaryProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndTitleProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcQuery;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;

import java.util.*;
import java.util.stream.Stream;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = QueryContract.DSL)
public interface PostgresQueryDslRepository extends QueryContract {

    @Override
    @JdbcQuery.DSL
    List<Employee> getAllEmployeeHardcoded();

    @Override
    @JdbcQuery.DSL
    Set<Employee> getAllEmployeeSetFromProperties();

    @Override
    @JdbcQuery.DSL
    Employee[] getAllEmployeeArrayFromYml();

    @Override
    @JdbcQuery.DSL
    Stream<Employee> getAllEmployeeStreamFromFile();

    @Override
    @JdbcQuery.DSL
    List<Employee> getAllEmployeeListWithCondition(Integer id);

    @Override
    @JdbcQuery.DSL
    Employee getOneEmployeeWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcQuery.DSL
    Optional<Employee> getEmployeeOptionalWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcQuery.DSL
    Employee getOneEmployeeWithConditionMapping(String conditionOne, String conditionTwo);

    @Override
    @JdbcQuery.DSL
    Employee getOneEmployeeWithConditionAndRowMapper(Integer id);

    @Override
    @JdbcQuery.DSL
    Employee getOneEmployeeWithConditionAndComplexRowMapper(Integer id);

    @Override
    @JdbcQuery.DSL
    Employee getOneEmployeeWithObjectCondition(DepartmentEmployee departmentEmployee);

    @Override
    @JdbcQuery.DSL
    EmployeeProjection getOneEmployeeWithConditionWithProjection(Integer id);

    @Override
    @JdbcQuery.DSL
    EmployeeAndSalaryProjection getOneEmployeeWithConditionWithComplexProjection(Integer id);

    @Override
    @JdbcQuery.DSL
    EmployeeAndTitleProjection getOneEmployeeWithConditionWithComplexProjectionImpl(Integer id);

    @Override
    @JdbcQuery.DSL
    Date getBirthdateEmployeeWithCondition(Integer id);

    @Override
    @JdbcQuery.DSL
    Map<String, Object> getEmployeeMapWithCondition(Integer id);

    @Override
    @JdbcQuery.DSL
    List<Employee> getAllEmployeeWithConditionArray(Integer... ids);

    @Override
    @JdbcQuery.DSL
    List<Employee> getAllEmployeeWithConditionArrayAndMapping(Integer... ids);

    @Override
    @JdbcQuery.DSL
    List<Employee> getAllEmployeeWithConditionListAndMapping(List<Integer> ids);

    @Override
    @JdbcQuery.DSL
    Optional<Department> getDepartmentOptionalWithCondition(String id);
}
