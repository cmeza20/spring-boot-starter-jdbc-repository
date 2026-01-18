package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ProcedureContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.ProcedureTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractProcedureTest extends AbstractException implements ProcedureTestContract {

    private final ProcedureContract procedureContract;

    @BeforeAll
    static void setup() {
        log.info("Setup ProcedureTest");
    }

    @Test
    @Override
    public void testProcedureEmployeeCountByGenderWithOutParameter() {
        log.info("Init testProcedureEmployeeCountByGenderWithOutParameter");

        Integer count = procedureContract.procedureEmployeeCountByGenderWithOutParameter("F");
        AssertUtils.assertObject(count, Integer.class);
        AssertUtils.assertEquals(count, 37, Integer.class);
    }

    @Test
    @Override
    public void testProcedureEmployeesByGenderWithCursor() {
        log.info("Init testProcedureEmployeesByGenderWithCursor");

        List<Employee> employees = procedureContract.procedureEmployeesByGenderWithCursor("M");
        AssertUtils.assertCollection(employees, 63);
    }

    @Test
    @Override
    public void testProcedureEmployeesByGenderWithCursorAndClassAttribute() {
        log.info("Init testProcedureEmployeesByGenderWithCursorAndClassAttribute");

        Employee request = new Employee();
        request.setGender("M");

        List<Employee> employees = procedureContract.procedureEmployeesByGenderWithCursorAndClassAttribute(request);
        AssertUtils.assertCollection(employees, 63);
    }

    @Test
    @Override
    public void testProcedureEmployeeByIdWithCursor() {
        log.info("Init testProcedureEmployeeByIdWithCursor");

        Optional<Employee> employeeOptional = procedureContract.procedureEmployeeByIdWithCursor(50);
        AssertUtils.assertOptional(employeeOptional, Employee.class);

        Employee employee = employeeOptional.get();
        AssertUtils.assertNotNull(employee);
        AssertUtils.assertEquals(employee.getFirstName(), "Yinghua", String.class);
        AssertUtils.assertEquals(employee.getLastName(), "Dredge", String.class);
        AssertUtils.assertEquals(employee.getGender(), "M", String.class);
    }
}
