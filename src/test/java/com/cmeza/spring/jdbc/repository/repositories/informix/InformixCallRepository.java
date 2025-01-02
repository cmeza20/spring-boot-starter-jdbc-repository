package com.cmeza.spring.jdbc.repository.repositories.informix;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcCall;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.InformixInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.CallContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface InformixCallRepository extends CallContract {

    @Override
    @Transactional(transactionManager = InformixInitializer.JDBC_TRANSACTION_MANAGER)
    @JdbcMapping(from = "id", to = "var_id", type = Types.VARCHAR)
    @JdbcMapping(from = "deptName", to = "var_dept_name", type = Types.VARCHAR)
    @JdbcCall(value = "sp_department_create", parameters = {":var_id", ":var_dept_name"})
    void callDepartmentCreate(Department department);
}
