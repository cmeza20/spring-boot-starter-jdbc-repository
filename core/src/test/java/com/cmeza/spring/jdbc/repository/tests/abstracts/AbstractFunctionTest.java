package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.FunctionContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.FunctionTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFunctionTest extends AbstractException implements FunctionTestContract {

    private static Employee employee;
    private final FunctionContract functionContract;

    @BeforeAll
    static void setup() {
        log.info("Setup FunctionTest");

        employee = new Employee()
                .setFirstName("Breannda")
                .setLastName("Billingsley")
                .setGender("M");
    }

    @Test
    @Override
    public void testFunctionSumWithOutParameter() {
        log.info("Init testFunctionSumWithOutParameter");

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
        log.info("Init testFunctionMultiplicationWithReturn");

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
        log.info("Init testFunctionEmployeesByGenderWithCursor");

        List<Employee> employees = functionContract.functionEmployeesByGenderWithCursor("F");
        AssertUtils.assertCollection(employees, 37);
    }

    @Test
    @Override
    public void testFunctionEmployeeNamesWithOutParameters() {
        log.info("Init testFunctionEmployeeNamesWithOutParameters");

        Map<String, Object> employeeNames = functionContract.functionEmployeeNamesWithOutParameters(42);
        AssertUtils.assertNotNull(employeeNames);

        String firstName = (String) employeeNames.get("out_first_name");
        AssertUtils.assertNotNull(firstName);
        AssertUtils.assertEquals(firstName, "Magy", String.class);

        String lastName = (String) employeeNames.get("out_last_name");
        AssertUtils.assertNotNull(lastName);
        AssertUtils.assertEquals(lastName, "Stamatiou", String.class);
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursor() {
        log.info("Init testFunctionEmployeesByObjectWithCursor");

        Optional<Employee> employeeOptional = functionContract.functionEmployeesByObjectWithCursor(employee);
        assertEmployeeTest(employeeOptional);
    }

    @Test
    @Override
    public void testFunctionEmployeesByObjectWithCursorAndClassAttributes() {
        log.info("Init testFunctionEmployeesByObjectWithCursorAndClassAttributes");

        Optional<Employee> employeeOptional = functionContract.functionEmployeesByObjectWithCursorAndClassAttributes(employee);
        assertEmployeeTest(employeeOptional);
    }

    private void assertEmployeeTest(Optional<Employee> employeeOptional) {
        AssertUtils.assertOptional(employeeOptional, Employee.class);

        Employee response = employeeOptional.get();
        AssertUtils.assertEquals(response.getFirstName(), employee.getFirstName(), String.class);
        AssertUtils.assertEquals(response.getLastName(), employee.getLastName(), String.class);
        AssertUtils.assertEquals(response.getGender(), employee.getGender(), String.class);
    }
}
