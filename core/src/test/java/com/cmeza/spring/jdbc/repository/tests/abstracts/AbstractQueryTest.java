package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.models.DepartmentEmployee;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndSalaryProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeAndTitleProjection;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.records.EmployeeRecord;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.QueryTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractQueryTest extends AbstractException implements QueryTestContract {

    protected final QueryContract queryRepository;

    @BeforeAll
    static void setup() {
        log.info("Setup QueryTest");
    }

    @Test
    @Override
    public void testGetAllEmployeeHardcoded() {
        log.info("Init testGetAllEmployeeHardcoded");

        AssertUtils.assertCollection(queryRepository.getAllEmployeeHardcoded(), 100);
    }

    @Test
    @Override
    public void testGetAllEmployeeSetFromProperties() {
        log.info("Init testGetAllEmployeeSetFromProperties");

        AssertUtils.assertCollection(queryRepository.getAllEmployeeSetFromProperties(), 100);
    }

    @Test
    @Override
    public void testGetAllEmployeeArrayFromYml() {
        log.info("Init testGetAllEmployeeArrayFromYml");

        AssertUtils.assertArray(queryRepository.getAllEmployeeArrayFromYml(), 100);
    }

    @Test
    @Override
    public void testGetAllEmployeeStreamFromFile() {
        log.info("Init testGetAllEmployeeStreamFromFile");

        AssertUtils.assertStream(queryRepository.getAllEmployeeStreamFromFile(), 100);
    }

    @Test
    @Override
    public void testGetAllEmployeeListWithCondition() {
        log.info("Init testGetAllEmployeeListWithCondition");

        Integer idEmployee = 80;
        AssertUtils.assertCollection(queryRepository.getAllEmployeeListWithCondition(idEmployee), 20);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithConditionParam() {
        log.info("Init testGetOneEmployeeWithConditionParam");

        Integer idEmployee = 1;
        AssertUtils.assertObject(queryRepository.getOneEmployeeWithConditionParam(idEmployee), Employee.class);
    }

    @Test
    @Override
    public void testGetEmployeeOptionalWithConditionParam() {
        log.info("Init testGetEmployeeOptionalWithConditionParam");

        Integer idEmployee = 1;
        AssertUtils.assertOptional(queryRepository.getEmployeeOptionalWithConditionParam(idEmployee), Employee.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithConditionMapping() {
        log.info("Init testGetOneEmployeeWithConditionMapping");

        String conditionOne = "Eberhardt";
        String conditionTwo = "M";
        AssertUtils.assertObject(queryRepository.getOneEmployeeWithConditionMapping(conditionOne, conditionTwo), Employee.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithConditionAndRowMapper() {
        log.info("Init testGetOneEmployeeWithConditionAndRowMapper");

        Integer idEmployee = 1;
        Employee employee = queryRepository.getOneEmployeeWithConditionAndRowMapper(idEmployee);
        AssertUtils.assertObject(employee, Employee.class);
        AssertUtils.assertEquals(employee.getId(), 1, Integer.class);
        AssertUtils.assertEquals(employee.getFirstName(), "Georgi", String.class);
        AssertUtils.assertEquals(employee.getGender(), "M", String.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithConditionAndComplexRowMapper() {
        log.info("Init testGetOneEmployeeWithConditionAndComplexRowMapper");

        Integer idEmployee = 1;
        Employee employee = queryRepository.getOneEmployeeWithConditionAndComplexRowMapper(idEmployee);
        AssertUtils.assertObject(employee, Employee.class);
        AssertUtils.assertNotNull(employee.getSalary());
        AssertUtils.assertEquals(employee.getSalary().getAmount(), 60117d, Double.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithObjectCondition() {
        log.info("Init testGetOneEmployeeWithObjectCondition");

        DepartmentEmployee departmentEmployee = new DepartmentEmployee()
                .setEmployee(new Employee().setId(1))
                .setDepartment(new Department().setId("d005"));
        Employee employee = queryRepository.getOneEmployeeWithObjectCondition(departmentEmployee);
        AssertUtils.assertObject(employee, Employee.class);
        AssertUtils.assertEquals(employee.getFirstName(), "Georgi", String.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithObjectConditionAndClassAttribute() {
        log.info("Init testGetOneEmployeeWithObjectConditionAndClassAttribute");

        DepartmentEmployee departmentEmployee = new DepartmentEmployee()
                .setEmployee(new Employee().setId(1))
                .setDepartment(new Department().setId("d005"));
        Employee employee = queryRepository.getOneEmployeeWithObjectConditionAndClassAttribute(departmentEmployee);
        AssertUtils.assertObject(employee, Employee.class);
        AssertUtils.assertEquals(employee.getFirstName(), "Georgi", String.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithConditionWithProjection() {
        log.info("Init testGetOneEmployeeWithConditionWithProjection");

        Integer idEmployee = 1;
        EmployeeProjection employeeProjection = queryRepository.getOneEmployeeWithConditionWithProjection(idEmployee);
        AssertUtils.assertObject(employeeProjection, EmployeeProjection.class);
        AssertUtils.assertEquals(employeeProjection.getFirstName(), "Georgi", String.class);
        AssertUtils.assertEquals(employeeProjection.getLastName(), "Facello", String.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithConditionWithComplexProjection() {
        log.info("Init testGetOneEmployeeWithConditionWithComplexProjection");

        Integer idEmployee = 1;
        EmployeeAndSalaryProjection employeeProjection = queryRepository.getOneEmployeeWithConditionWithComplexProjection(idEmployee);
        AssertUtils.assertObject(employeeProjection, EmployeeAndSalaryProjection.class);
        AssertUtils.assertEquals(employeeProjection.getAmount(), 60117d, Double.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeWithConditionWithComplexProjectionImpl() {
        log.info("Init testGetOneEmployeeWithConditionWithComplexProjectionImpl");

        Integer idEmployee = 1;
        EmployeeAndTitleProjection employeeProjection = queryRepository.getOneEmployeeWithConditionWithComplexProjectionImpl(idEmployee);
        AssertUtils.assertObject(employeeProjection, EmployeeAndTitleProjection.class);
        AssertUtils.assertNotNull(employeeProjection.getTitleObject());
        AssertUtils.assertEquals(employeeProjection.getTitleObject().getTitle(), "Senior Engineer", String.class);
    }

    @Test
    @Override
    public void testGetBirtdateEmployeeWithCondition() {
        log.info("Init testGetBirtdateEmployeeWithCondition");

        Integer idEmployee = 1;

        LocalDate localDate = LocalDate.of(1953, Month.SEPTEMBER, 2);
        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        Date birtdate = queryRepository.getBirthdateEmployeeWithCondition(idEmployee);

        AssertUtils.assertNotNull(birtdate);
        AssertUtils.assertEquals(birtdate.toInstant(), instant, Instant.class);
    }

    @Test
    @Override
    public void testGetEmployeeMapWithCondition() {
        log.info("Init testGetEmployeeMapWithCondition");

        Integer idEmployee = 1;
        Map<String, Object> map = queryRepository.getEmployeeMapWithCondition(idEmployee);
        AssertUtils.assertMap(map);
        AssertUtils.assertEquals(map.get("id"), equalsNumber("1", classId()), classId());
    }

    @Test
    @Override
    public void testGetAllEmployeeWithConditionArray() {
        log.info("Init testGetAllEmployeeWithConditionArray");

        AssertUtils.assertCollection(queryRepository.getAllEmployeeWithConditionArray(1, 2, 3), 3);
    }

    @Test
    public void testGetAllEmployeeWithConditionArrayAndMapping() {
        log.info("Init testGetAllEmployeeWithConditionArrayAndMapping");

        AssertUtils.assertCollection(queryRepository.getAllEmployeeWithConditionArrayAndMapping(1, 2, 3, 4), 4);
    }

    @Test
    @Override
    public void testGetAllEmployeeWithConditionListAndMapping() {
        log.info("Init testGetAllEmployeeWithConditionListAndMapping");

        AssertUtils.assertCollection(queryRepository.getAllEmployeeWithConditionListAndMapping(Arrays.asList(1, 2, 3, 4, 5)), 5);
    }

    @Test
    @Override
    public void testGetDepartmentOptionalWithCondition() {
        log.info("Init testGetDepartmentOptionalWithCondition");

        String id = "d002";
        Optional<Department> departmentOptional = queryRepository.getDepartmentOptionalWithCondition(id);
        AssertUtils.assertOptional(departmentOptional, Department.class);

        Department department = departmentOptional.get();
        AssertUtils.assertEquals(department.getId(), id, String.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeRecordWithConditionMapping() {
        log.info("Init testGetOneEmployeeRecordWithConditionMapping");

        String conditionOne = "Eberhardt";
        String conditionTwo = "M";
        AssertUtils.assertObject(queryRepository.getOneEmployeeRecordWithConditionMapping(conditionOne, conditionTwo), EmployeeRecord.class);
    }

    @Test
    @Override
    public void testGetOneEmployeeRecordWithConditionAndComplexRowMapper() {
        log.info("Init testGetOneEmployeeRecordWithConditionAndComplexRowMapper");

        Integer idEmployee = 1;
        EmployeeRecord employee = queryRepository.getOneEmployeeRecordWithConditionAndComplexRowMapper(idEmployee);
        AssertUtils.assertObject(employee, EmployeeRecord.class);
        AssertUtils.assertNotNull(employee.salary());
        AssertUtils.assertEquals(employee.salary().amount(), 60117d, Double.class);
    }
}
