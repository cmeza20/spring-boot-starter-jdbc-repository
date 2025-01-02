package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Department;

public interface ExecuteContract {

    int deleteDepartmentWithReturningInt(String id);

    int deleteEmployeeWithReturningInt(Integer ids);

    int deleteEmployeesWithArrayAndReturningInt(String... names);

    void executeCallDepartmentCreateWithoutResult(Department department);
}
