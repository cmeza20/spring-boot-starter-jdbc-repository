package com.cmeza.spring.jdbc.repository.repositories.sqlserver;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcInsert;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.InsertContract;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional(transactionManager = SqlServerInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = SqlServerInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface SqlServerInsertRepository extends InsertContract {

    @Override
    @JdbcInsert(table = "department", columns = {"id", "dept_name"})
    int insertDepartmentWithReturnInt(String id, String deptName);

    @Override
    @JdbcMapping(from = "anotherId", to = "id", type = Types.VARCHAR)
    @JdbcMapping(from = "anotherDeptName", to = "deptName", type = Types.VARCHAR)
    @JdbcInsert(table = "department", columns = {"id", "dept_name"})
    Integer insertDepartmentWithParamAndReturnInteger(String anotherId, String anotherDeptName);

    @Override
    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"}, generatedKeyColumns = "id")
    KeyHolder insertEmployeeWithModelAndReturnKeyHolder(Employee employee);

    @Override
    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"})
    int[] insertEmployeeBatchListAndReturnArray(List<Employee> employees);

    @Override
    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"})
    int[] insertEmployeeBatchSetAndReturnArray(Set<Employee> employees);

    @Override
    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"})
    int[] insertEmployeeBatchArrayAndReturnArray(Employee[] employees);

    @Override
    @JdbcMapping(from = "another_first_name", to = "first_name", type = Types.VARCHAR)
    @JdbcMapping(from = "another_gender", to = "gender", type = Types.VARCHAR)
    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"})
    int[] insertEmployeeBatchMapAndReturnArray(Map<String, Object> map);
}
