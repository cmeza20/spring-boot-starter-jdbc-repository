package com.cmeza.spring.jdbc.repository.repositories.oracle.update;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.OracleInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.UpdateContract;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@Transactional(transactionManager = OracleInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA_TEST, repositoryTemplateBeanName = OracleInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface OracleRawUpdateRepository extends UpdateContract {

    @Override
    @JdbcMapping(from = "firstName", to = "fname", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "lname", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "gen", type = Types.VARCHAR)
    @JdbcMapping(from = "id", to = "employeeId", type = Types.INTEGER)
    @JdbcRawUpdate("update test.employee set first_name = :fname, last_name = :lname, gender = :gen " +
            "where id = :employeeId")
    int updateWithReturningInt(Employee employee);

    @Override
    @JdbcRawUpdate(value = "update test.employee set first_name = :firstName, last_name = :lastName, gender = :gender " +
            "where id = :id", keyColumnNames = "id")
    KeyHolder updateWithReturningHolder(Employee employee);

    @Override
    @JdbcRawUpdate(value = "update test.department_employee de set department_id = :departmentId from test.employee e " +
            "where de.employee_id = e.id and e.first_name = :firstName and e.last_name = :lastName",
            keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    @Override
    @JdbcRawUpdate(value = "update test.department_employee de set department_id = :deptName " +
            "from test.employee e inner join test.department d on (1=1) " +
            "where (de.employee_id = e.id) and (de.department_id = d.id) and (e.first_name = :firstName and e.last_name = :lastName)",
            keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
