package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.InsertContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.InsertTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractInsertTest extends AbstractException implements InsertTestContract {

    private static Employee employee;
    private static List<Employee> employeeList;
    private static Set<Employee> employeeSet;
    private static Employee[] employeeArray;
    private static Map<String, Object> employeeMap;

    private final InsertContract insertContract;
    private final QueryContract queryContract;
    private final ExecuteContract executeContract;

    @BeforeAll
    static void setup() {
        log.info("Setup InsertTest");

        employee = new Employee()
                .setFirstName("Jean")
                .setLastName("Mantech")
                .setGender("M")
                .setBirthDate(new Date())
                .setHireDate(new Date());
        employee = new Employee()
                .setFirstName("Jean")
                .setLastName("Mantech")
                .setGender("M")
                .setBirthDate(new Date())
                .setHireDate(new Date());

        employeeList = List.of(
                new Employee()
                        .setFirstName("Luis")
                        .setLastName("List")
                        .setGender("M")
                        .setBirthDate(new Date())
                        .setHireDate(new Date()),
                new Employee()
                        .setFirstName("Maria")
                        .setLastName("List")
                        .setGender("F")
                        .setBirthDate(new Date())
                        .setHireDate(new Date()),
                new Employee()
                        .setFirstName("Rodolfo")
                        .setLastName("List")
                        .setGender("M")
                        .setBirthDate(new Date())
                        .setHireDate(new Date())
        );

        employeeSet = Set.of(
                new Employee()
                        .setFirstName("Andres")
                        .setLastName("Set")
                        .setGender("M")
                        .setBirthDate(new Date())
                        .setHireDate(new Date()),
                new Employee()
                        .setFirstName("Estefania")
                        .setLastName("Set")
                        .setGender("F")
                        .setBirthDate(new Date())
                        .setHireDate(new Date())
        );

        employeeArray = List.of(
                new Employee()
                        .setFirstName("Jhon")
                        .setLastName("Array")
                        .setGender("M")
                        .setBirthDate(new Date())
                        .setHireDate(new Date()),
                new Employee()
                        .setFirstName("Lugo")
                        .setLastName("Array")
                        .setGender("F")
                        .setBirthDate(new Date())
                        .setHireDate(new Date())
        ).toArray(new Employee[0]);

        employeeMap = new HashMap<>();
        employeeMap.put("another_first_name", "Carlos");
        employeeMap.put("last_name", "Map");
        employeeMap.put("gender", "M");
        employeeMap.put("birth_date", new Date());
        employeeMap.put("hire_date", new Date());
    }

    @Test
    @Override
    public void testInsertDepartmentWithReturnInt() {
        log.info("Init testInsertDepartmentWithReturnInt");

        int count = insertContract.insertDepartmentWithReturnInt("d100", "Gerency");
        AssertUtils.assertEquals(count, 1, Integer.class);

        int delete = executeContract.deleteDepartmentWithReturningInt("d100");
        AssertUtils.assertEquals(delete, 1, Integer.class);
    }

    @Test
    @Override
    public void testInsertDepartmentWithParamAndReturnInteger() {
        log.info("Init testInsertDepartmentWithParamAndReturnInteger");

        Integer count = insertContract.insertDepartmentWithParamAndReturnInteger("d200", "Gerency 2");
        AssertUtils.assertEquals(count, 1, Integer.class);

        int delete = executeContract.deleteDepartmentWithReturningInt("d200");
        AssertUtils.assertEquals(delete, 1, Integer.class);
    }

    @Test
    @Override
    public void testInsertEmployeeWithModelAndReturnKeyHolder() {
        log.info("Init testInsertEmployeeWithModelAndReturnKeyHolder");

        KeyHolder keyHolder = insertContract.insertEmployeeWithModelAndReturnKeyHolder(employee);
        AssertUtils.assertNotNull(keyHolder);

        Employee employeeResult = queryContract.getOneEmployeeWithConditionMapping(employee.getFirstName(), employee.getGender());
        AssertUtils.assertNotNull(employeeResult);

        int delete = executeContract.deleteEmployeeWithReturningInt(employeeResult.getId());
        AssertUtils.assertEquals(delete, 1, Integer.class);
    }

    @Test
    @Override
    public void testInsertEmployeeBatchListAndReturnArray() {
        log.info("Init testInsertEmployeeBatchListAndReturnArray");

        int[] counts = insertContract.insertEmployeeBatchListAndReturnArray(employeeList);
        AssertUtils.assertNotNull(counts);
        AssertUtils.assertObject(counts, int[].class);
        AssertUtils.assertArray(counts, 3);

        int delete = executeContract.deleteEmployeesWithArrayAndReturningInt("Luis", "Maria", "Rodolfo");
        AssertUtils.assertNotNull(delete);
        AssertUtils.assertEquals(delete, 3, Integer.class);
    }

    @Test
    @Override
    public void testInsertEmployeeBatchSetAndReturnArray() {
        log.info("Init testInsertEmployeeBatchSetAndReturnArray");

        int[] counts = insertContract.insertEmployeeBatchSetAndReturnArray(employeeSet);
        AssertUtils.assertNotNull(counts);
        AssertUtils.assertObject(counts, int[].class);
        AssertUtils.assertArray(counts, 2);

        int delete = executeContract.deleteEmployeesWithArrayAndReturningInt("Andres", "Estefania");
        AssertUtils.assertNotNull(delete);
        AssertUtils.assertEquals(delete, 2, Integer.class);
    }

    @Test
    @Override
    public void testInsertEmployeeBatchArrayAndReturnArray() {
        log.info("Init testInsertEmployeeBatchArrayAndReturnArray");

        int[] counts = insertContract.insertEmployeeBatchArrayAndReturnArray(employeeArray);
        AssertUtils.assertNotNull(counts);
        AssertUtils.assertObject(counts, int[].class);
        AssertUtils.assertArray(counts, 2);

        int delete = executeContract.deleteEmployeesWithArrayAndReturningInt("Jhon", "Lugo");
        AssertUtils.assertNotNull(delete);
        AssertUtils.assertEquals(delete, 2, Integer.class);
    }

    @Test
    @Override
    public void testInsertEmployeeBatchArrayAndReturnArrayAndClassAttributes() {
        log.info("Init testInsertEmployeeBatchArrayAndReturnArrayAndClassAttributes");

        int[] counts = insertContract.insertEmployeeBatchArrayAndReturnArrayAndClassAttributes(employeeArray);
        AssertUtils.assertNotNull(counts);
        AssertUtils.assertObject(counts, int[].class);
        AssertUtils.assertArray(counts, 2);

        int delete = executeContract.deleteEmployeesWithArrayAndReturningInt("Jhon", "Lugo");
        AssertUtils.assertNotNull(delete);
        AssertUtils.assertEquals(delete, 2, Integer.class);
    }

    @Test
    @Override
    public void testInsertEmployeeBatchMapAndReturnArray() {
        log.info("Init testInsertEmployeeBatchMapAndReturnArray");

        int[] counts = insertContract.insertEmployeeBatchMapAndReturnArray(employeeMap);
        AssertUtils.assertNotNull(counts);
        AssertUtils.assertObject(counts, int[].class);
        AssertUtils.assertArray(counts, 1);

        int delete = executeContract.deleteEmployeesWithArrayAndReturningInt("Carlos");
        AssertUtils.assertNotNull(delete);
        AssertUtils.assertEquals(delete, 1, Integer.class);
    }
}
