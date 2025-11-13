package com.cmeza.spring.jdbc.repository.repositories.mysql;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.MysqlInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ProcedureContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Transactional(transactionManager = MysqlInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = MysqlInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface MysqlProcedureRepository extends ProcedureContract {

    @Override
    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcProcedure(name = "sp_employees_count_by_gender_with_out_parameter", outParameters = @Parameter(value = "result", type = Types.INTEGER))
    Integer procedureEmployeeCountByGenderWithOutParameter(String gender);

    @Override
    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcProcedure(name = "sp_employees_by_gender")
    List<Employee> procedureEmployeesByGenderWithCursor(String gender);

    @Override
    @JdbcProcedure(name = "sp_employees_by_id")
    Optional<Employee> procedureEmployeeByIdWithCursor(@JdbcParam("id") Integer id);

}
