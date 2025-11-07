package com.cmeza.spring.jdbc.repository.repositories.postgres.pagination;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.contracts.PaginationContract;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawPagination;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = PaginationContract.DSL_RAW)
public interface PostgresRawPaginationDslRepository extends PaginationContract {
    @Override
    @JdbcRawPagination.DSL
    JdbcPage<Employee> paginationEmployeesWithoutParameter();

    @Override
    @JdbcRawPagination.DSL
    JdbcPage<Employee> paginationEmployeesWithCondition(Integer id);

    @Override
    @JdbcRawPagination.DSL
    JdbcPage<Employee> paginationEmployeesWithConditionAndPageRequest(Integer from, Integer to, JdbcPageRequest pageRequest);

    @Override
    @JdbcRawPagination.DSL
    JdbcPage<EmployeeProjection> paginationEmployeeProjectionWithConditionAndPageRequestAndCountQuery(Integer max, JdbcPageRequest pageRequest);
}
