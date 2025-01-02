package com.cmeza.spring.jdbc.repository.tests.contracts;

public interface PaginationTestContract {
    void testPaginationEmployeesWithoutParameter();

    void testPaginationEmployeesWithCondition();

    void testPaginationEmployeesWithConditionAndPageRequest();

    void testPaginationEmployeesWithConditionAndPageRequestBounds();

    void testPaginationEmployeeProyectionWithConditionAndPageRequestAndCountQuery();
}
