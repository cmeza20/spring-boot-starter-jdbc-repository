package com.cmeza.spring.jdbc.repository.tests.contracts;

public interface QueryTestContract {

    void testGetAllEmployeeHardcoded();

    void testGetAllEmployeeSetFromProperties();

    void testGetAllEmployeeArrayFromYml();

    void testGetAllEmployeeStreamFromFile();

    void testGetAllEmployeeListWithCondition();

    void testGetOneEmployeeWithConditionParam();

    void testGetEmployeeOptionalWithConditionParam();

    void testGetOneEmployeeWithConditionMapping();

    void testGetOneEmployeeWithConditionAndRowMapper();

    void testGetOneEmployeeWithConditionAndComplexRowMapper();

    void testGetOneEmployeeWithObjectCondition();

    void testGetOneEmployeeWithConditionWithProjection();

    void testGetOneEmployeeWithConditionWithComplexProjection();

    void testGetOneEmployeeWithConditionWithComplexProjectionImpl();

    void testGetBirtdateEmployeeWithCondition();

    void testGetEmployeeMapWithCondition();

    void testGetAllEmployeeWithConditionArray();

    void testGetAllEmployeeWithConditionArrayAndMapping();

    void testGetAllEmployeeWithConditionListAndMapping();

    void testGetDepartmentOptionalWithCondition();
}
