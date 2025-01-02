package com.cmeza.spring.jdbc.repository.repositories.informix;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.InformixInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ProcedureContract;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Transactional(transactionManager = InformixInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface InformixProcedureRepository extends ProcedureContract {

    @Override
    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcProcedure(name = "sp_employees_count_by_gender_with_out_parameter", outParameters = @Parameter(value = "result", type = Types.INTEGER))
    Integer procedureEmployeeCountByGenderWithOutParameter(String gender);

    @Override
    @JdbcProcedure(name = "Informix_UnsupportedOperationException")
    List<Employee> procedureEmployeesByGenderWithCursor(String gender);

    @Override
    @JdbcProcedure(name = "Informix_UnsupportedOperationException")
    Optional<Employee> procedureEmployeeByIdWithCursor(Integer id);

}
