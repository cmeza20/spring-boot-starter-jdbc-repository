package com.cmeza.spring.jdbc.repository.repositories.informix.update;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawUpdate;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.InformixInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.UpdateContract;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@Transactional(transactionManager = InformixInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface InformixRawUpdateRepository extends UpdateContract {

    @Override
    @JdbcMapping(from = "firstName", to = "fname", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "lname", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "gen", type = Types.VARCHAR)
    @JdbcMapping(from = "id", to = "employeeId", type = Types.INTEGER)
    @JdbcRawUpdate("update sch_test.employee set first_name = :fname, last_name = :lname, gender = :gen " +
            "where id = :employeeId")
    int updateWithReturningInt(Employee employee);

    @Override
    @JdbcRawUpdate(value = "INFORMIX_UnsupportedOperationException")
    KeyHolder updateWithReturningHolder(Employee employee);

    @Override
    @JdbcRawUpdate(value = "INFORMIX_UnsupportedOperationException")
    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    @Override
    @JdbcRawUpdate(value = "INFORMIX_UnsupportedOperationException")
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
