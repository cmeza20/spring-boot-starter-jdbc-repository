package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import org.springframework.jdbc.support.KeyHolder;

public interface UpdateContract {
    String DSL = "UpdateRepository";
    String DSL_RAW = DSL + "Raw";

    int updateWithReturningInt(Employee employee);

    KeyHolder updateWithReturningHolder(Employee employee);

    KeyHolder updateComplexReturningHolder(String firstName, String lastName, String departmentId);

    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
