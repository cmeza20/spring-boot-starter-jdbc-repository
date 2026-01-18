package com.cmeza.spring.jdbc.repository.tests.contracts;

public interface PaginationTestContract {
    void testPaginationEmployeesWithoutParameter();

    void testPaginationEmployeesWithCondition();

    void testPaginationEmployeesWithConditionAndClassAttributes();

    void testPaginationEmployeesWithConditionAndPageRequest();

    void testPaginationEmployeesWithConditionAndPageRequestBounds();

    void testPaginationEmployeeProjectionWithConditionAndPageRequestAndCountQuery();
}
