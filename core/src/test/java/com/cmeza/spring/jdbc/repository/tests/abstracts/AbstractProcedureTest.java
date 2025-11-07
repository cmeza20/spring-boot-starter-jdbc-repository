package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ProcedureContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.ProcedureTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractProcedureTest extends AbstractException implements ProcedureTestContract {

    private final ProcedureContract procedureContract;

    @Test
    @Override
    public void testProcedureEmployeeCountByGenderWithOutParameter() {
        Integer count = procedureContract.procedureEmployeeCountByGenderWithOutParameter("F");
        AssertUtils.assertObject(count, Integer.class);
        AssertUtils.assertEquals(count, 37, Integer.class);
    }

    @Test
    @Override
    public void testProcedureEmployeesByGenderWithCursor() {
        List<Employee> employees = procedureContract.procedureEmployeesByGenderWithCursor("M");
        AssertUtils.assertCollection(employees, 63);
    }

    @Test
    @Override
    public void testProcedureEmployeeByIdWithCursor() {
        Optional<Employee> employeeOptional = procedureContract.procedureEmployeeByIdWithCursor(50);
        AssertUtils.assertOptional(employeeOptional, Employee.class);

        Employee employee = employeeOptional.get();
        AssertUtils.assertNotNull(employee);
        AssertUtils.assertEquals(employee.getFirstName(), "Yinghua", String.class);
        AssertUtils.assertEquals(employee.getLastName(), "Dredge", String.class);
        AssertUtils.assertEquals(employee.getGender(), "M", String.class);
    }
}
