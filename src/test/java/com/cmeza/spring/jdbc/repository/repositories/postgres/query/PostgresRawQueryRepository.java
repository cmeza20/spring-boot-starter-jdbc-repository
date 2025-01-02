package com.cmeza.spring.jdbc.repository.repositories.postgres.query;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcRawQuery;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.mappers.EmployeeAndSalaryRowMapper;
import com.cmeza.spring.jdbc.repository.mappers.EmployeeRowMapper;
import com.cmeza.spring.jdbc.repository.mappers.projections.EmployeeAndTitleProjectionRowMapper;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.models.DepartmentEmployee;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndSalaryProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndTitleProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;

import java.sql.Types;
import java.util.*;
import java.util.stream.Stream;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface PostgresRawQueryRepository extends QueryContract {

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee")
    List<Employee> getAllEmployeeHarcoded();

    @Override
    @JdbcRawQuery(value = "${properties.employee.query.all}")
    Set<Employee> getAllEmployeeSetFromProperties();

    @Override
    @JdbcRawQuery(value = "${yml.employee.query.all}")
    Employee[] getAllEmployeeArrayFromYml();

    @Override
    @JdbcRawQuery(value = "file:/jdbc-employee.sql")
    Stream<Employee> getAllEmployeeStreamFromFile();

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee where id > :id")
    List<Employee> getAllEmployeeListWithCondition(Integer id);

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee where id = :idParam")
    Employee getOneEmployeeWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee where id = :idParam")
    Optional<Employee> getEmployeeOptionalWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcMapping(from = "conditionOne", to = "name", type = Types.VARCHAR)
    @JdbcMapping(from = "conditionTwo", to = "gen", type = Types.VARCHAR)
    @JdbcRawQuery(value = "select * from sch_test.employee where first_name = :name and gender = :gen")
    Employee getOneEmployeeWithConditionMapping(String conditionOne, String conditionTwo);

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee where id = :id", mapper = EmployeeRowMapper.class)
    Employee getOneEmployeeWithConditionAndRowMapper(Integer id);

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee e " +
            "inner join sch_test.salary s on s.employee_id = e.id where e.id = :id", mapper = EmployeeAndSalaryRowMapper.class)
    Employee getOneEmployeeWithConditionAndComplexRowMapper(Integer id);

    @Override
    @JdbcMapping(from = "employee.id", to = "employee_id", type = Types.BIGINT)
    @JdbcMapping(from = "department.id", to = "department_id", type = Types.VARCHAR)
    @JdbcRawQuery(value = "select e.* from sch_test.department_employee de " +
            "inner join sch_test.employee e on e.id = de.employee_id " +
            "where e.id = :employee_id and de.department_id = :department_id")
    Employee getOneEmployeeWithObjectCondition(DepartmentEmployee departmentEmployee);

    @Override
    @JdbcRawQuery(value = "select e.id, e.first_name, e.last_name from sch_test.employee e where e.id = :id")
    EmployeeProjection getOneEmployeeWithConditionWithProjection(Integer id);

    @Override
    @JdbcRawQuery(value = "select e.id, e.first_name, e.last_name, s.amount from sch_test.employee e " +
            "inner join sch_test.salary s on s.employee_id = e.id where e.id = :id")
    EmployeeAndSalaryProjection getOneEmployeeWithConditionWithComplexProjection(Integer id);

    @Override
    @JdbcRawQuery(value = "select e.id, e.first_name, e.last_name, t.title from sch_test.employee e " +
            "inner join sch_test.title t on t.employee_id = e.id where e.id = :id", mapper = EmployeeAndTitleProjectionRowMapper.class)
    EmployeeAndTitleProjection getOneEmployeeWithConditionWithComplexProjectionImpl(Integer id);

    @Override
    @JdbcRawQuery(value = "select birth_date from sch_test.employee where id = :id")
    Date getBirtdateEmployeeWithCondition(Integer id);

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee where id = :id")
    Map<String, Object> getEmployeeMapWithCondition(Integer id);

    @Override
    @JdbcRawQuery(value = "select * from sch_test.employee where id in (:ids)")
    List<Employee> getAllEmployeeWithConditionArray(Integer... ids);

    @Override
    @JdbcMapping(from = "ids", to = "mappingIds", type = Types.INTEGER)
    @JdbcRawQuery(value = "select * from sch_test.employee where id in (:mappingIds)")
    List<Employee> getAllEmployeeWithConditionArrayAndMapping(Integer... ids);

    @Override
    @JdbcMapping(from = "ids", to = "employees", type = Types.INTEGER)
    @JdbcRawQuery(value = "select * from sch_test.employee where id in (:employees)")
    List<Employee> getAllEmployeeWithConditionListAndMapping(List<Integer> ids);

    @Override
    @JdbcRawQuery(value = "select * from sch_test.department where id = :id")
    Optional<Department> getDepartmentOptionalWithCondition(String id);
}
