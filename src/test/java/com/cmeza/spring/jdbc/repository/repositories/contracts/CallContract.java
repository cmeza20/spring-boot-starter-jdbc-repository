package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Department;

public interface CallContract {
    void callDepartmentCreate(Department department);
}
