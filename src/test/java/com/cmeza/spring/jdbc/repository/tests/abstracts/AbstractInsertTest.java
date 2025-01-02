package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.InsertContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.InsertTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;

@RequiredArgsConstructor
public abstract class AbstractInsertTest extends AbstractException implements InsertTestContract {

    private final InsertContract insertContract;
    private final QueryContract queryContract;
    private final ExecuteContract executeContract;

    @Test
    @Override
    public void testInsertDepartmentWithReturnInt() {
        int count = insertContract.insertDepartmentWithReturnInt("d100", "Gerency");
        AssertUtils.assertEquals(count, 1, Integer.class);

        int delete = executeContract.deleteDepartmentWithReturningInt("d100");
        AssertUtils.assertEquals(delete, 1, Integer.class);
    }

    @Test
    @Override
    public void testInsertDepartmentWithParamAndReturnInteger() {
        Integer count = insertContract.insertDepartmentWithParamAndReturnInteger("d200", "Gerency 2");
        AssertUtils.assertEquals(count, 1, Integer.class);

        int delete = executeContract.deleteDepartmentWithReturningInt("d200");
        AssertUtils.assertEquals(delete, 1, Integer.class);
    }

    @Test
    @Override
    public void testInsertEmployeeWithModelAndReturnKeyHolder() {
        Employee employee = new Employee()
                .setFirstName("Jean")
                .setLastName("Mantech")
                .setGender("M")
                .setBirthDate(new Date())
                .setHireDate(new Date());
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
        List<Employee> employees = List.of(
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
        int[] counts = insertContract.insertEmployeeBatchListAndReturnArray(employees);
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
        Set<Employee> employees = Set.of(
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
        int[] counts = insertContract.insertEmployeeBatchSetAndReturnArray(employees);
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
        Employee[] employees = List.of(
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
        int[] counts = insertContract.insertEmployeeBatchArrayAndReturnArray(employees);
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
        Map<String, Object> employee = new HashMap<>();
        employee.put("another_first_name", "Carlos");
        employee.put("last_name", "Map");
        employee.put("gender", "M");
        employee.put("birth_date", new Date());
        employee.put("hire_date", new Date());

        int[] counts = insertContract.insertEmployeeBatchMapAndReturnArray(employee);
        AssertUtils.assertNotNull(counts);
        AssertUtils.assertObject(counts, int[].class);
        AssertUtils.assertArray(counts, 1);

        int delete = executeContract.deleteEmployeesWithArrayAndReturningInt("Carlos");
        AssertUtils.assertNotNull(delete);
        AssertUtils.assertEquals(delete, 1, Integer.class);
    }
}
