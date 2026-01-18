package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.CallContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.CallTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public abstract class AbstractCallTest extends AbstractException implements CallTestContract {

    private static Department department;
    private final CallContract callContract;
    private final QueryContract queryContract;
    private final ExecuteContract executeContract;

    @BeforeAll
    static void setup() {
        log.info("Setup CallTest");

        department = new Department()
                .setId("d888")
                .setDeptName("IT")
                .setIgnoreAttribute("Ignored value");
    }

    @Test
    @Override
    public void testCallDepartmentCreate() {
        log.info("Init testCallDepartmentCreate");

        callContract.callDepartmentCreate(department);
        assertTest();
    }

    @Test
    @Override
    public void testCallDepartmentCreateWithClassAttributes() {
        log.info("Init testCallDepartmentCreateWithClassAttributes");

        callContract.callDepartmentCreateWithClassAttributes(department);
        assertTest();
    }

    private void assertTest() {
        Optional<Department> departmentOptional = queryContract.getDepartmentOptionalWithCondition(department.getId());
        AssertUtils.assertOptional(departmentOptional, Department.class);

        executeContract.deleteDepartmentWithReturningInt(department.getId());
    }
}
