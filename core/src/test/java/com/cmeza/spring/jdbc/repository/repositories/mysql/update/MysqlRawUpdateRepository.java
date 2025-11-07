package com.cmeza.spring.jdbc.repository.repositories.mysql.update;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
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
public interface MysqlRawUpdateRepository extends UpdateContract {

    @Override
    @JdbcMapping(from = "firstName", to = "fname", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "lname", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "gen", type = Types.VARCHAR)
    @JdbcMapping(from = "id", to = "employeeId", type = Types.INTEGER)
    @JdbcRawUpdate("update sch_test.employee set first_name = :fname, last_name = :lname, gender = :gen " +
            "where id = :employeeId")
    int updateWithReturningInt(Employee employee);

    @Override
    @JdbcRawUpdate(value = "update sch_test.employee set first_name = :firstName, last_name = :lastName, gender = :gender " +
            "where id = :id", keyColumnNames = "id")
    KeyHolder updateWithReturningHolder(Employee employee);

    @Override
    @JdbcRawUpdate(value = "update sch_test.department_employee de inner join sch_test.employee e on de.employee_id = e.id " +
            "set department_id = :departmentId " +
            "where e.first_name = :firstName and e.last_name = :lastName",
            keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    @Override
    @JdbcRawUpdate(value = "update sch_test.department_employee de " +
            "inner join sch_test.employee e on de.employee_id = e.id " +
            "inner join sch_test.department d on de.department_id = d.id " +
            "set department_id = :deptName " +
            "where (e.first_name = :firstName and e.last_name = :lastName)",
            keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
