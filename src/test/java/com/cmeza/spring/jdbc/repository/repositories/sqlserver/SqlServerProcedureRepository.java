package com.cmeza.spring.jdbc.repository.repositories.sqlserver;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.SqlServerInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ProcedureContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = SqlServerInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface SqlServerProcedureRepository extends ProcedureContract {

    @Override
    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcProcedure(name = "sp_employees_count_by_gender_with_out_parameter", outParameters = @Parameter(value = "@res", type = Types.INTEGER))
    Integer procedureEmployeeCountByGenderWithOutParameter(String gender);

    @Override
    @Transactional(transactionManager = SqlServerInitializer.JDBC_TRANSACTION_MANAGER)
    @JdbcMapping(from = "gender", to = "@var_gender", type = Types.VARCHAR)
    @JdbcProcedure(name = "sp_employees_by_gender")
    List<Employee> procedureEmployeesByGenderWithCursor(String gender);

    @Override
    @Transactional(transactionManager = SqlServerInitializer.JDBC_TRANSACTION_MANAGER)
    @JdbcProcedure(name = "sp_employees_by_id")
    Optional<Employee> procedureEmployeeByIdWithCursor(@JdbcParam("@id") Integer id);

}
