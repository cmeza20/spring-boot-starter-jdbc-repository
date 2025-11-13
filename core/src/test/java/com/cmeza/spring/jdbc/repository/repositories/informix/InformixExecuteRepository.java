package com.cmeza.spring.jdbc.repository.repositories.informix;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcExecute;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.InformixInitializer;
import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface InformixExecuteRepository extends ExecuteContract {

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
    @Transactional(transactionManager = InformixInitializer.JDBC_TRANSACTION_MANAGER)
    @JdbcMapping(from = "id", to = "var_id", type = Types.VARCHAR)
    @JdbcMapping(from = "deptName", to = "var_dept_name", type = Types.VARCHAR)
    @JdbcExecute("call sch_test.sp_department_create(:var_id, :var_dept_name)")
    void executeCallDepartmentCreateWithoutResult(Department department);
}
