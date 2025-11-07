package com.cmeza.spring.jdbc.repository.tests.contracts;

public interface ProcedureTestContract {
    void testProcedureEmployeeCountByGenderWithOutParameter();

    void testProcedureEmployeesByGenderWithCursor();

    void testProcedureEmployeeByIdWithCursor();
}
