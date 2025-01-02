package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;

public interface PaginationContract {
    JdbcPage<Employee> paginationEmployeesWithoutParameter();

    JdbcPage<Employee> paginationEmployeesWithCondition(Integer id);

    JdbcPage<Employee> paginationEmployeesWithConditionAndPageRequest(Integer from, Integer to, JdbcPageRequest pageRequest);

    JdbcPage<EmployeeProjection> paginationEmployeeProyectionWithConditionAndPageRequestAndCountQuery(Integer max, JdbcPageRequest pageRequest);

}
