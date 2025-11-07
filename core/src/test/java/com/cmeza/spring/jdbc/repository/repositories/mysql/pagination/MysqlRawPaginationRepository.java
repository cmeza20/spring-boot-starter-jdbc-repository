package com.cmeza.spring.jdbc.repository.repositories.mysql.pagination;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcRawPagination;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.JdbcRawCountQuery;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.MysqlInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.contracts.PaginationContract;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;

@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = MysqlInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN)
public interface MysqlRawPaginationRepository extends PaginationContract {
    @Override
    @JdbcRawPagination(value = "select * from sch_test.employee")
    JdbcPage<Employee> paginationEmployeesWithoutParameter();

    @Override
    @JdbcRawPagination(value = "select * from sch_test.employee where id = :id")
    JdbcPage<Employee> paginationEmployeesWithCondition(Integer id);

    @Override
    @JdbcRawPagination(value = "select * from sch_test.employee where id between :from and :to")
    JdbcPage<Employee> paginationEmployeesWithConditionAndPageRequest(Integer from, Integer to, JdbcPageRequest pageRequest);

    @Override
    @JdbcRawCountQuery("select (:max - 50)")
    @JdbcRawPagination(value = "select * from sch_test.employee where id <= :max")
    JdbcPage<EmployeeProjection> paginationEmployeeProjectionWithConditionAndPageRequestAndCountQuery(Integer max, JdbcPageRequest pageRequest);
}
