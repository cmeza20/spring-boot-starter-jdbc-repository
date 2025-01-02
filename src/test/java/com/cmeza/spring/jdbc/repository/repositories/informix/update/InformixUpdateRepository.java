package com.cmeza.spring.jdbc.repository.repositories.informix.update;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.InformixInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.UpdateContract;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@Transactional(transactionManager = InformixInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface InformixUpdateRepository extends UpdateContract {

    @Override
    @JdbcMapping(from = "firstName", to = "fname", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "lname", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "gen", type = Types.VARCHAR)
    @JdbcMapping(from = "id", to = "employeeId", type = Types.INTEGER)
    @JdbcUpdate(table = "employee", updateSets = {"first_name = :fname", "last_name = :lname", "gender = :gen"}, where = "id = :employeeId")
    int updateWithReturningInt(Employee employee);

    @Override
    @JdbcUpdate(table = "INFORMIX_UnsupportedOperationException", updateSets = "FIELD=VALUE")
    KeyHolder updateWithReturningHolder(Employee employee);

    @Override
    @JdbcUpdate(table = "INFORMIX_UnsupportedOperationException", updateSets = "FIELD=VALUE")
    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    @Override
    @JdbcUpdate(table = "INFORMIX_UnsupportedOperationException", updateSets = "FIELD=VALUE")
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
