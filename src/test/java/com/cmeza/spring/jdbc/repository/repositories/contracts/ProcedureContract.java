package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Employee;

import java.util.List;
import java.util.Optional;

public interface ProcedureContract {
    Integer procedureEmployeeCountByGenderWithOutParameter(String gender);

    List<Employee> procedureEmployeesByGenderWithCursor(String gender);

    Optional<Employee> procedureEmployeeByIdWithCursor(Integer id);
}
