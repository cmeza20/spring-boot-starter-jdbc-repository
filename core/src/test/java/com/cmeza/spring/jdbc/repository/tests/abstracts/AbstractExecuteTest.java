package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.ExecuteTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractExecuteTest extends AbstractException implements ExecuteTestContract {

    private static Department department;
    private final ExecuteContract executeContract;
    private final QueryContract queryContract;

    @BeforeAll
    static void setup() {
        log.info("Setup ExecuteTest");

        department = new Department()
                .setId("d999")
                .setDeptName("Gerency")
                .setIgnoreAttribute("Ignored value");
    }

    @Test
    @Override
    public void testExecuteCallDepartmentCreateWithoutResult() {
        log.info("Init testExecuteCallDepartmentCreateWithoutResult");

        executeContract.executeCallDepartmentCreateWithoutResult(department);
        assertTest();
    }

    @Test
    @Override
    public void testExecuteCallDepartmentCreateWithoutResultAndClassAttributes() {
        log.info("Init testExecuteCallDepartmentCreateWithoutResultAndClassAttributes");

        executeContract.executeCallDepartmentCreateWithoutResultAndClassAttributes(department);
        assertTest();
    }

    private void assertTest() {
        //Get
        Optional<Department> departmentOptional = queryContract.getDepartmentOptionalWithCondition(department.getId());
        AssertUtils.assertOptional(departmentOptional, Department.class);
        AssertUtils.assertEquals(departmentOptional.get().getDeptName(), department.getDeptName(), String.class);

        //Delete
        executeContract.deleteDepartmentWithReturningInt(department.getId());
        departmentOptional = queryContract.getDepartmentOptionalWithCondition(department.getId());
        Assertions.assertThat(departmentOptional).isNotPresent();
    }
}
