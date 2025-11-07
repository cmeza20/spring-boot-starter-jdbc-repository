package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Department;

public interface CallContract {
    String DSL = "CallRepository";

    void callDepartmentCreate(Department department);
}
