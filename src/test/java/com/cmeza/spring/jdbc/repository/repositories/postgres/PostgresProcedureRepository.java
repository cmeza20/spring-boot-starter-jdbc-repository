package com.cmeza.spring.jdbc.repository.repositories.postgres;

import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.annotations.methods.operations.JdbcProcedure;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ProcedureContract;

import java.util.List;
import java.util.Optional;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface PostgresProcedureRepository extends ProcedureContract {

    @Override
    @JdbcProcedure(name = "POSTGRES_UnsupportedOperationException")
    Integer procedureEmployeeCountByGenderWithOutParameter(String gender);

    @Override
    @JdbcProcedure(name = "POSTGRES_UnsupportedOperationException")
    List<Employee> procedureEmployeesByGenderWithCursor(String gender);

    @Override
    @JdbcProcedure(name = "POSTGRES_UnsupportedOperationException")
    Optional<Employee> procedureEmployeeByIdWithCursor(Integer id);

}
