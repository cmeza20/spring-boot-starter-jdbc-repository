package com.cmeza.spring.jdbc.repository.repositories.sqlserver;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@Transactional(transactionManager = SqlServerInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = SqlServerInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface SqlServerExecuteRepository extends ExecuteContract {

    @Override
    @JdbcExecute("delete from sch_test.department where id = :id")
    int deleteDepartmentWithReturningInt(@JdbcParam("id") String id);

    @Override
    @JdbcExecute("delete from sch_test.employee where id = :id")
    int deleteEmployeeWithReturningInt(Integer id);

    @Override
    @JdbcExecute("delete from sch_test.employee where first_name in (:names)")
    int deleteEmployeesWithArrayAndReturningInt(String... names);

    @Override
    @JdbcMapping(from = "id", to = "var_id", type = Types.VARCHAR)
    @JdbcMapping(from = "deptName", to = "var_dept_name", type = Types.VARCHAR)
    @JdbcExecute("exec sch_test.sp_department_create :var_id, :var_dept_name")
    void executeCallDepartmentCreateWithoutResult(Department department);
}
