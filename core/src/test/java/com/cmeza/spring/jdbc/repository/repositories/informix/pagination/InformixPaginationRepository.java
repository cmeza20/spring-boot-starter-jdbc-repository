package com.cmeza.spring.jdbc.repository.repositories.informix.pagination;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcPagination;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcCountQuery;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcMapping;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.JdbcParam;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.configurations.InformixInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.contracts.PaginationContract;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;

import java.sql.Types;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = InformixInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface InformixPaginationRepository extends PaginationContract {
    @Override
    @JdbcPagination(table = "employee")
    JdbcPage<Employee> paginationEmployeesWithoutParameter();

    @Override
    @JdbcPagination(table = "employee", where = "id = :id")
    JdbcPage<Employee> paginationEmployeesWithCondition(Integer id);

    @Override
    @JdbcPagination(table = "employee", where = "t.id between :from and :to", alias = "t")
    JdbcPage<Employee> paginationEmployeesWithConditionAndPageRequest(Integer from, Integer to, JdbcPageRequest pageRequest);

    @Override
    @JdbcMapping(from = "max", to = "max_c", type = Types.INTEGER)
    @JdbcCountQuery(table = "employee", columns = "(:max_c - 50)", where = "id = 1")
    @JdbcPagination(table = "employee", where = "id <= :max_c")
    JdbcPage<EmployeeProjection> paginationEmployeeProjectionWithConditionAndPageRequestAndCountQuery(@JdbcParam("max") Integer max, JdbcPageRequest pageRequest);
}
