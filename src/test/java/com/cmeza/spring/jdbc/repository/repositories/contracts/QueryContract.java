package com.cmeza.spring.jdbc.repository.repositories.contracts;

import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.models.DepartmentEmployee;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndSalaryProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndTitleProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;

import java.util.*;
import java.util.stream.Stream;

public interface QueryContract {
    List<Employee> getAllEmployeeHarcoded();

    Set<Employee> getAllEmployeeSetFromProperties();

    Employee[] getAllEmployeeArrayFromYml();

    Stream<Employee> getAllEmployeeStreamFromFile();

    List<Employee> getAllEmployeeListWithCondition(Integer id);

    Employee getOneEmployeeWithConditionParam(Integer anotherId);

    Optional<Employee> getEmployeeOptionalWithConditionParam(Integer anotherId);

    Employee getOneEmployeeWithConditionMapping(String conditionOne, String conditionTwo);

    Employee getOneEmployeeWithConditionAndRowMapper(Integer id);

    Employee getOneEmployeeWithConditionAndComplexRowMapper(Integer id);

    Employee getOneEmployeeWithObjectCondition(DepartmentEmployee departmentEmployee);

    EmployeeProjection getOneEmployeeWithConditionWithProjection(Integer id);

    EmployeeAndSalaryProjection getOneEmployeeWithConditionWithComplexProjection(Integer id);

    EmployeeAndTitleProjection getOneEmployeeWithConditionWithComplexProjectionImpl(Integer id);

    Date getBirtdateEmployeeWithCondition(Integer id);

    Map<String, Object> getEmployeeMapWithCondition(Integer id);

    List<Employee> getAllEmployeeWithConditionArray(Integer... ids);

    List<Employee> getAllEmployeeWithConditionArrayAndMapping(Integer... ids);

    List<Employee> getAllEmployeeWithConditionListAndMapping(List<Integer> ids);

    Optional<Department> getDepartmentOptionalWithCondition(String id);

}
