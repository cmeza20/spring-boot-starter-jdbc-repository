package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.FunctionContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.FunctionTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractFunctionTest extends AbstractException implements FunctionTestContract {

    private final FunctionContract functionContract;

    @Test
    @Override
    public void testFunctionSumWithOutParameter() {
        Double numberOne = 25.5d;
        Double numberTwo = 16.9d;
        Double sum = functionContract.functionSumWithOutParameter(numberOne, numberTwo);
        AssertUtils.assertNotNull(sum);
        AssertUtils.assertObject(sum, Double.class);
        AssertUtils.assertEquals(sum, numberOne + numberTwo, Double.class);
    }

    @Test
    @Override
    public void testFunctionMultiplicationWithReturn() {
        Double numberOne = 33.7d;
        Double numberTwo = 2.6d;
        Double multiplication = functionContract.functionMultiplicationWithReturn(numberOne, numberTwo);
        AssertUtils.assertNotNull(multiplication);
        AssertUtils.assertObject(multiplication, Double.class);
        AssertUtils.assertEquals(multiplication, numberOne * numberTwo, Double.class);
    }

    @Test
    @Override
    public void testFunctionEmployeesByGenderWithCursor() {
        List<Employee> employees = functionContract.functionEmployeesByGenderWithCursor("F");
        AssertUtils.assertCollection(employees, 37);
    }

    @Test
    @Override
    public void testFunctionEmployeeNamesWithOutParameters() {
        Map<String, Object> employeeNames = functionContract.functionEmployeeNamesWithOutParameters(42);
        AssertUtils.assertNotNull(employeeNames);

        String firstName = (String)employeeNames.get("out_first_name");
        AssertUtils.assertNotNull(firstName);
        AssertUtils.assertEquals(firstName, "Magy", String.class);

        String lastName = (String)employeeNames.get("out_last_name");
        AssertUtils.assertNotNull(lastName);
        AssertUtils.assertEquals(lastName, "Stamatiou", String.class);
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursor() {
        Employee request = new Employee()
                .setFirstName("Breannda")
                .setLastName("Billingsley")
                .setGender("M");
        Optional<Employee> employee = functionContract.functionEmployeesByObjectWithCursor(request);
        AssertUtils.assertOptional(employee, Employee.class);

        Employee response = employee.get();
        AssertUtils.assertEquals(response.getFirstName(), request.getFirstName(), String.class);
        AssertUtils.assertEquals(response.getLastName(), request.getLastName(), String.class);
        AssertUtils.assertEquals(response.getGender(), request.getGender(), String.class);
    }
}
