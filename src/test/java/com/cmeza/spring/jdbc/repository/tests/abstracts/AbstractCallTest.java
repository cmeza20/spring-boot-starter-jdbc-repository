package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.CallContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.CallTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractCallTest extends AbstractException implements CallTestContract {

    private final CallContract callContract;
    private final QueryContract queryContract;
    private final ExecuteContract executeContract;

    @Test
    @Override
    public void testCallDepartmentCreate() {
        Department department = new Department()
                .setId("d888")
                .setDeptName("IT");
        callContract.callDepartmentCreate(department);

        Optional<Department> departmentOptional = queryContract.getDepartmentOptionalWithCondition(department.getId());
        AssertUtils.assertOptional(departmentOptional, Department.class);

        executeContract.deleteDepartmentWithReturningInt(department.getId());

    }
}
