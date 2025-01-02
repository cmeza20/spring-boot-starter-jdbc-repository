package com.cmeza.spring.jdbc.repository.repositories.oracle;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.OracleInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = OracleInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface OracleExecuteRepository extends ExecuteContract {

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
    @Transactional(transactionManager = OracleInitializer.JDBC_TRANSACTION_MANAGER)
    @JdbcMapping(from = "id", to = "var_id", type = Types.VARCHAR)
    @JdbcMapping(from = "deptName", to = "var_dept_name", type = Types.VARCHAR)
    @JdbcExecute("call sch_test.sp_department_create(:var_id, :var_dept_name)")
    void executeCallDepartmentCreateWithoutResult(Department department);
}
