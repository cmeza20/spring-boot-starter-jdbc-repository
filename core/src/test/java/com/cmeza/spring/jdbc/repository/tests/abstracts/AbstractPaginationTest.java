package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.projections.EmployeeProjection;
import com.cmeza.spring.jdbc.repository.repositories.contracts.PaginationContract;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPage;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import com.cmeza.spring.jdbc.repository.tests.contracts.PaginationTestContract;
import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
public abstract class AbstractPaginationTest extends AbstractException implements PaginationTestContract {

    private final PaginationContract paginationContract;

    @Test
    @Override
    public void testPaginationEmployeesWithoutParameter() {
        JdbcPage<Employee> page = paginationContract.paginationEmployeesWithoutParameter();

        AssertUtils.assertObject(page, JdbcPage.class);
        AssertUtils.assertCollection(page.getContent(), 10);
        AssertUtils.assertEquals(page.getCurrentPage(), 1, Integer.class);
        AssertUtils.assertEquals(page.getTotalPages(), 10, Integer.class);
        AssertUtils.assertEquals(page.getTotalElements(), 100L, Long.class);
    }

    @Test
    @Override
    public void testPaginationEmployeesWithCondition() {
        Integer employeeId = 100;
        JdbcPage<Employee> page = paginationContract.paginationEmployeesWithCondition(employeeId);

        AssertUtils.assertObject(page, JdbcPage.class);
        AssertUtils.assertCollection(page.getContent(), 1);
        AssertUtils.assertEquals(page.getCurrentPage(), 1, Integer.class);
        AssertUtils.assertEquals(page.getTotalPages(), 1, Integer.class);
        AssertUtils.assertEquals(page.getTotalElements(), 1L, Long.class);
    }

    @Test
    @Override
    public void testPaginationEmployeesWithConditionAndPageRequest() {
        JdbcPageRequest pageRequest = JdbcPageRequest.ofPage(2, 50);
        JdbcPage<Employee> page = paginationContract.paginationEmployeesWithConditionAndPageRequest(1, 100, pageRequest);

        AssertUtils.assertObject(page, JdbcPage.class);
        AssertUtils.assertCollection(page.getContent(), 50);
        AssertUtils.assertEquals(page.getCurrentPage(), 2, Integer.class);
        AssertUtils.assertEquals(page.getTotalPages(), 2, Integer.class);
        AssertUtils.assertEquals(page.getTotalElements(), 100L, Long.class);
    }

    @Test
    @Override
    public void testPaginationEmployeesWithConditionAndPageRequestBounds() {
        JdbcPageRequest pageRequest = JdbcPageRequest.ofBounds(54, 9);
        JdbcPage<Employee> page = paginationContract.paginationEmployeesWithConditionAndPageRequest(1, 60, pageRequest);

        AssertUtils.assertObject(page, JdbcPage.class);
        AssertUtils.assertCollection(page.getContent(), 6);
        AssertUtils.assertEquals(page.getCurrentPage(), 7, Integer.class);
        AssertUtils.assertEquals(page.getTotalPages(), 7, Integer.class);
        AssertUtils.assertEquals(page.getTotalElements(), 60L, Long.class);
        AssertUtils.assertEquals(page.isLast(), true, Boolean.class);
    }

    @Test
    @Override
    public void testPaginationEmployeeProyectionWithConditionAndPageRequestAndCountQuery() {
        JdbcPageRequest pageRequest = JdbcPageRequest.ofPage(1, 10);
        JdbcPage<EmployeeProjection> page = paginationContract.paginationEmployeeProjectionWithConditionAndPageRequestAndCountQuery(100, pageRequest);

        AssertUtils.assertObject(page, JdbcPage.class);
        AssertUtils.assertCollection(page.getContent(), 10);
        AssertUtils.assertEquals(page.getCurrentPage(), 1, Integer.class);
        AssertUtils.assertEquals(page.getTotalPages(), 5, Integer.class);
        AssertUtils.assertEquals(page.getTotalElements(), 50L, Long.class);
        AssertUtils.assertEquals(page.isFirst(), true, Boolean.class);
    }
}
