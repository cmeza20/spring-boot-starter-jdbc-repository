package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FunctionContract {
    String DSL = "FunctionRepository";

    Double functionSumWithOutParameter(Double numberOne, Double numberTwo);

    Double functionMultiplicationWithReturn(Double numberOne, Double numberTwo);

    List<Employee> functionEmployeesByGenderWithCursor(String gender);

    Map<String, Object> functionEmployeeNamesWithOutParameters(Integer id);

    Optional<Employee> functionEmployeesByObjectWithCursor(Employee employee);
}
