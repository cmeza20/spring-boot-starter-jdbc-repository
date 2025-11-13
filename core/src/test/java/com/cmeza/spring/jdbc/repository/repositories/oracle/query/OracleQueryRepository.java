package com.cmeza.spring.jdbc.repository.repositories.oracle.query;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcQuery;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
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

@JdbcRepository(schema = TestConstants.SCHEMA_TEST, repositoryTemplateBeanName = OracleInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface OracleQueryRepository extends QueryContract {

    @Override
    @JdbcQuery(table = "employee")
    List<Employee> getAllEmployeeHardcoded();

    @Override
    @JdbcQuery(table = "${properties.employee.query.table}")
    Set<Employee> getAllEmployeeSetFromProperties();

    @Override
    @JdbcQuery(table = "${yml.employee.query.table}")
    Employee[] getAllEmployeeArrayFromYml();

    @Override
    @JdbcQuery(table = "file:/jdbc-employee-table.sql")
    Stream<Employee> getAllEmployeeStreamFromFile();

    @Override
    @JdbcQuery(table = "employee", where = "id > :id")
    List<Employee> getAllEmployeeListWithCondition(Integer id);

    @Override
    @JdbcQuery(table = "employee", where = "id = :idParam")
    Employee getOneEmployeeWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcQuery(table = "employee", where = "id = :idParam")
    Optional<Employee> getEmployeeOptionalWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @Override
    @JdbcMapping(from = "conditionOne", to = "name", type = Types.VARCHAR)
    @JdbcMapping(from = "conditionTwo", to = "gen", type = Types.VARCHAR)
    @JdbcQuery(table = "employee", where = "first_name = :name and gender = :gen")
    Employee getOneEmployeeWithConditionMapping(String conditionOne, String conditionTwo);

    @Override
    @JdbcQuery(table = "employee", where = "id = :id", mapper = EmployeeRowMapper.class)
    Employee getOneEmployeeWithConditionAndRowMapper(Integer id);

    @Override
    @JdbcJoinTable(table = "salary", alias = "s", on = "s.employee_id = e.id", join = JdbcJoinTable.Join.INNER)
    @JdbcQuery(table = "employee", alias = "e", where = "e.id = :id", mapper = EmployeeAndSalaryRowMapper.class)
    Employee getOneEmployeeWithConditionAndComplexRowMapper(Integer id);

    @Override
    @JdbcMapping(from = "employee.id", to = "employee_id", type = Types.BIGINT)
    @JdbcMapping(from = "department.id", to = "department_id", type = Types.VARCHAR)
    @JdbcJoinTable(table = "employee", alias = "e", on = "e.id = de.employee_id", join = JdbcJoinTable.Join.INNER)
    @JdbcQuery(table = "department_employee", alias = "de", where = "e.id = :employee_id and de.department_id = :department_id")
    Employee getOneEmployeeWithObjectCondition(DepartmentEmployee departmentEmployee);

    @Override
    @JdbcQuery(table = "employee", alias = "e", columns = {"e.id", "e.first_name", "e.last_name"}, where = "e.id = :id")
    EmployeeProjection getOneEmployeeWithConditionWithProjection(Integer id);

    @Override
    @JdbcJoinTable(table = "salary", alias = "s", on = "s.employee_id = e.id", join = JdbcJoinTable.Join.INNER)
    @JdbcQuery(table = "employee", alias = "e", columns = {"e.id", "e.first_name", "e.last_name", "s.amount"}, where = "e.id = :id")
    EmployeeAndSalaryProjection getOneEmployeeWithConditionWithComplexProjection(Integer id);

    @Override
    @JdbcJoinTable(table = "title", alias = "t", on = "t.employee_id = e.id", join = JdbcJoinTable.Join.INNER)
    @JdbcQuery(table = "employee", alias = "e", columns = {"e.id", "e.first_name", "e.last_name", "t.title"}, where = "e.id = :id", mapper = EmployeeAndTitleProjectionRowMapper.class)
    EmployeeAndTitleProjection getOneEmployeeWithConditionWithComplexProjectionImpl(Integer id);

    @Override
    @JdbcQuery(table = "employee", columns = "birth_date", where = "id = :id")
    Date getBirthdateEmployeeWithCondition(Integer id);

    @Override
    @JdbcQuery(table = "employee", where = "id = :id")
    Map<String, Object> getEmployeeMapWithCondition(Integer id);

    @Override
    @JdbcQuery(table = "employee", where = "id in (:ids)")
    List<Employee> getAllEmployeeWithConditionArray(Integer... ids);

    @Override
    @JdbcMapping(from = "ids", to = "mappingIds", type = Types.INTEGER)
    @JdbcQuery(table = "employee", where = "id in (:mappingIds)")
    List<Employee> getAllEmployeeWithConditionArrayAndMapping(Integer... ids);

    @Override
    @JdbcMapping(from = "ids", to = "employees", type = Types.INTEGER)
    @JdbcQuery(table = "employee", where = "id in (:employees)")
    List<Employee> getAllEmployeeWithConditionListAndMapping(List<Integer> ids);

    @Override
    @JdbcQuery(table = "department", where = "id = :id")
    Optional<Department> getDepartmentOptionalWithCondition(String id);
}
