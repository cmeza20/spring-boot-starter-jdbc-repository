package com.cmeza.spring.jdbc.repository.repositories.mysql.update;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcJoinTable;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.MysqlInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.UpdateContract;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@Transactional(transactionManager = MysqlInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = MysqlInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface MysqlUpdateRepository extends UpdateContract {

    @Override
    @JdbcMapping(from = "firstName", to = "fname", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "lname", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "gen", type = Types.VARCHAR)
    @JdbcMapping(from = "id", to = "employeeId", type = Types.INTEGER)
    @JdbcUpdate(table = "employee", updateSets = {"first_name = :fname", "last_name = :lname", "gender = :gen"}, where = "id = :employeeId")
    int updateWithReturningInt(Employee employee);

    @Override
    @JdbcUpdate(table = "employee", updateSets = {"first_name = :firstName", "last_name = :lastName", "gender = :gender"}, where = "id = :id", keyColumnNames = "id")
    KeyHolder updateWithReturningHolder(Employee employee);

    @Override
    @JdbcJoinTable(table = "employee", alias = "e", on = "de.employee_id = e.id", position = JdbcJoinTable.JoinPosition.START)
    @JdbcUpdate(table = "department_employee", alias = "de", updateSets = {"department_id = :departmentId"}, where = "e.first_name = :firstName and e.last_name = :lastName", keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    @Override
    @JdbcJoinTable(table = "employee", alias = "e", on = "de.employee_id = e.id", position = JdbcJoinTable.JoinPosition.START)
    @JdbcJoinTable(table = "department", alias = "d", on = "de.department_id = d.id", position = JdbcJoinTable.JoinPosition.START)
    @JdbcUpdate(table = "department_employee", alias = "de", updateSets = {"department_id = :deptName"},
            where = "(e.first_name = :firstName and e.last_name = :lastName)",
            keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
