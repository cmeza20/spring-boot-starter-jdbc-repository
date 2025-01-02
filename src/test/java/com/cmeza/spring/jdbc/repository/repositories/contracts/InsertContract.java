package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface InsertContract {

    int insertDepartmentWithReturnInt(String id, String deptName);

    Integer insertDepartmentWithParamAndReturnInteger(String anotherId, String anotherDeptName);

    KeyHolder insertEmployeeWithModelAndReturnKeyHolder(Employee employee);

    int[] insertEmployeeBatchListAndReturnArray(List<Employee> employees);

    int[] insertEmployeeBatchSetAndReturnArray(Set<Employee> employees);

    int[] insertEmployeeBatchArrayAndReturnArray(Employee[] employees);

    int[] insertEmployeeBatchMapAndReturnArray(Map<String, Object> map);

}
