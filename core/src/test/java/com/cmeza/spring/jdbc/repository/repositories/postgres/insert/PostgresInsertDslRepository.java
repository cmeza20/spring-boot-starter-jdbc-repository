package com.cmeza.spring.jdbc.repository.repositories.postgres.insert;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.initializers.PostgresInitializer;
import com.cmeza.spring.jdbc.repository.models.Employee;
import com.cmeza.spring.jdbc.repository.repositories.contracts.InsertContract;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.annotations.methods.operations.JdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional(transactionManager = PostgresInitializer.JDBC_TRANSACTION_MANAGER)
@JdbcRepository(schema = TestConstants.SCHEMA, repositoryTemplateBeanName = PostgresInitializer.JDBC_REPOSITORY_TEMPLATE_BEAN, dslName = InsertContract.DSL)
public interface PostgresInsertDslRepository extends InsertContract {

    @Override
    @JdbcInsert.DSL
    int insertDepartmentWithReturnInt(String id, String deptName);

    @Override
    @JdbcInsert.DSL
    Integer insertDepartmentWithParamAndReturnInteger(String anotherId, String anotherDeptName);

    @Override
    @JdbcInsert.DSL
    KeyHolder insertEmployeeWithModelAndReturnKeyHolder(Employee employee);

    @Override
    @JdbcInsert.DSL
    int[] insertEmployeeBatchListAndReturnArray(List<Employee> employees);

    @Override
    @JdbcInsert.DSL
    int[] insertEmployeeBatchSetAndReturnArray(Set<Employee> employees);

    @Override
    @JdbcInsert.DSL
    int[] insertEmployeeBatchArrayAndReturnArray(Employee[] employees);

    @Override
    @JdbcInsert.DSL
    int[] insertEmployeeBatchMapAndReturnArray(Map<String, Object> map);
}
