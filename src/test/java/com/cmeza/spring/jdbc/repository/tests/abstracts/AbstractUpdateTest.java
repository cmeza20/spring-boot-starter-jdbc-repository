package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.UpdateContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.UpdateTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Map;

@RequiredArgsConstructor
public abstract class AbstractUpdateTest extends AbstractException implements UpdateTestContract {

    private final UpdateContract updateContract;
    private final QueryContract queryContract;

    @Test
    @Override
    public void testUpdateWithReturningInt() {
        Employee employee = new Employee()
                .setFirstName("Alberto")
                .setLastName("Rodriguez")
                .setGender("F")
                .setId(24);
        int update = updateContract.updateWithReturningInt(employee);
        AssertUtils.assertNotNull(update);
        AssertUtils.assertEquals(update, 1, Integer.class);

        Employee employeeDB = queryContract.getOneEmployeeWithConditionParam(24);
        AssertUtils.assertNotNull(employeeDB);
        AssertUtils.assertObject(employeeDB, Employee.class);
        AssertUtils.assertEquals(employeeDB.getFirstName(), employee.getFirstName(), String.class);
        AssertUtils.assertEquals(employeeDB.getLastName(), employee.getLastName(), String.class);
        AssertUtils.assertEquals(employeeDB.getGender(), employee.getGender(), String.class);
    }

    @Test
    @Override
    public void testUpdateWithReturningHolder() {
        Employee employee = new Employee()
                .setFirstName("Ana")
                .setLastName("Mendoza")
                .setGender("M")
                .setId(25);
        KeyHolder keyHolder = updateContract.updateWithReturningHolder(employee);
        AssertUtils.assertNotNull(keyHolder);
        AssertUtils.assertObject(keyHolder, KeyHolder.class);
        AssertUtils.assertEquals(keyHolder.getKey(), equalsNumber("25", classId()), classId());
    }

    @Test
    @Override
    public void testUpdateComplexReturningHolder() {
        KeyHolder keyHolder = updateContract.updateComplexReturningHolder("Yongqiao", "Berztiss", "d005");
        AssertUtils.assertNotNull(keyHolder);
        AssertUtils.assertObject(keyHolder, KeyHolder.class);

        Map<String, Object> keys = keyHolder.getKeys();

        AssertUtils.assertNotNull(keys);
        AssertUtils.assertNotNull(keys.get("employee_id"));
        AssertUtils.assertNotNull(keys.get("department_id"));
        AssertUtils.assertEquals(keys.get("employee_id"), equalsNumber("26", classId()), classId());
        AssertUtils.assertEquals(keys.get("department_id"), "d005", String.class);
    }

    @Test
    @Override
    public void testUpdateComplexTwoReturningHolder() {
        KeyHolder keyHolder = updateContract.updateComplexTwoReturningHolder("Divier", "Reistad", "d009");
        AssertUtils.assertNotNull(keyHolder);
        AssertUtils.assertObject(keyHolder, KeyHolder.class);

        Map<String, Object> keys = keyHolder.getKeys();
        AssertUtils.assertNotNull(keys);
        AssertUtils.assertNotNull(keys.get("employee_id"));
        AssertUtils.assertNotNull(keys.get("department_id"));
        AssertUtils.assertEquals(keys.get("employee_id"), equalsNumber("27", classId()), classId());
        AssertUtils.assertEquals(keys.get("department_id"), "d009", String.class);

    }
}
