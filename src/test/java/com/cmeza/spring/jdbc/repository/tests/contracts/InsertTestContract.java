package com.cmeza.spring.jdbc.repository.tests.contracts;

public interface InsertTestContract {

    void testInsertDepartmentWithReturnInt();

    void testInsertDepartmentWithParamAndReturnInteger();

    void testInsertEmployeeWithModelAndReturnKeyHolder();

    void testInsertEmployeeBatchListAndReturnArray();

    void testInsertEmployeeBatchSetAndReturnArray();

    void testInsertEmployeeBatchArrayAndReturnArray();

    void testInsertEmployeeBatchMapAndReturnArray();
}
