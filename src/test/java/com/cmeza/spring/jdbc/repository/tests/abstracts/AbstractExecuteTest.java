package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Department;
import com.cmeza.spring.jdbc.repository.repositories.contracts.ExecuteContract;
import com.cmeza.spring.jdbc.repository.repositories.contracts.QueryContract;
import com.cmeza.spring.jdbc.repository.tests.contracts.ExecuteTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractExecuteTest extends AbstractException implements ExecuteTestContract {

    private final ExecuteContract executeContract;
    private final QueryContract queryContract;

    @Test
    @Override
    public void testExecuteCallDepartmentCreateWithoutResult() {

        //Create
        Department department = new Department()
                .setId("d999")
                .setDeptName("Gerency");
        executeContract.executeCallDepartmentCreateWithoutResult(department);

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
