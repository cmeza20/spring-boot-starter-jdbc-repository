# Spring Boot Starter JDBC Repository [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.cmeza/spring-boot-starter-jdbc-repository/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.cmeza/spring-boot-starter-jdbc-repository)

Jdbc template repositories, inspired by Spring data Jpa

### Wiki ##

* [Get Started](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/Get-Started)
* [Properties](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/Properties)
* [@JdbcRepository annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcRepository-annotation)

***

### Methods
* [@JdbcQuery annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcQuery-annotation)
* [@JdbcRawQuery annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcRawQuery-annotation)
* [@JdbcPagination annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcPagination-annotation)
* [@JdbcRawPagination annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcRawPagination-annotation)
* [@JdbcUpdate annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcUpdate-annotation)
* [@JdbcRawUpdate annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcRawUpdate-annotation)
* [@JdbcInsert annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcInsert-annotation)
* [@JdbcFunction annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcFunction-annotation)
* [@JdbcProcedure annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcProcedure-annotation)
* [@JdbcCall annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcCall-annotation)
* [@JdbcExecute annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcExecute-annotation)
***

### Supports
* [@JdbcMapping annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcMapping-annotation)
* [@JdbcCountQuery annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcCountQuery-annotation)
* [@JdbcRawCountQuery annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcRawCountQuery-annotation)
* [@JdbcFromTable annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcFromTable-annotation)
* [@JdbcJoinTable annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcJoinTable-annotation)
***

### Parameters
* [@JdbcParam annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcParam-annotation)
* [@Parameter annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@Parameter-annotation)

***
### Mappers
* [JdbcRowMapper interface](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRowMapper)
* [JdbcProjectionRowMapper interface](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcProjectionRowMapper)
* [Support for projections](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/projections-support)

***
### Advanced
* [Interface JdbcRepositoryAware](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRepositoryAware)
* [Properties Placeholder](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/Properties-Placeholder)
* [JdbcRepositoryTemplate multi tenant](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRepositoryTemplate-multi-tenant)
* [JdbcRepositoryTemplate manual execute](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRepositoryTemplate)
* [NamingStrategy](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/NamingStrategy)


## Maven Integration ##

```xml
<dependency>
    <groupId>com.cmeza</groupId>
    <artifactId>spring-boot-starter-jdbc-repository</artifactId>
    <version>2.0.0</version>
</dependency>
```
## Minimal dependencies ##
- @EnableJdbcRepositories annotation and DataSource bean

```java
@EnableJdbcRepositories
@SpringBootApplication
public class SpringBootStarterJdbcRepositoryTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStarterJdbcRepositoryTestApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource datasource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

}
```

## PostgresSql Example ##

### @JdbcQuery
```java
@JdbcRepository
public interface EmployeeQueryRepository {
    //Query
    //------------------------------------------------------
    @JdbcQuery(table = "employee")
    List<Employee> getAllEmployeeHarcoded();

    @JdbcQuery(table = "employee", where = "id = :idParam")
    Optional<Employee> getEmployeeOptionalWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @JdbcMapping(from = "conditionOne", to = "name", type = Types.VARCHAR)
    @JdbcMapping(from = "conditionTwo", to = "gen", type = Types.VARCHAR)
    @JdbcQuery(table = "employee", where = "first_name = :name and gender = :gen")
    Employee getOneEmployeeWithConditionMapping(String conditionOne, String conditionTwo);

    @JdbcQuery(table = "employee", alias = "e", columns = {"e.id", "e.first_name", "e.last_name"}, where = "e.id = :id")
    EmployeeProjection getOneEmployeeWithConditionWithProjection(Integer id);
}
```

### @JdbcRawQuery
```java
@JdbcRepository
public interface EmployeeRawQueryRepository {
    //Raw Query
    //------------------------------------------------------

    @JdbcRawQuery(value = "${properties.employee.query.all}")
    Set<Employee> getAllEmployeeSetFromProperties();

    @JdbcRawQuery(value = "file:/jdbc-employee.sql")
    Stream<Employee> getAllEmployeeStreamFromFile();

    @JdbcRawQuery(value = "select * from sch_test.employee where id = :idParam")
    Employee getOneEmployeeWithConditionParam(@JdbcParam("idParam") Integer anotherId);

    @JdbcMapping(from = "employee.id", to = "employee_id", type = Types.BIGINT)
    @JdbcMapping(from = "department.id", to = "department_id", type = Types.VARCHAR)
    @JdbcRawQuery(value = "select e.* from sch_test.department_employee de " +
            "inner join sch_test.employee e on e.id = de.employee_id " +
            "where e.id = :employee_id and de.department_id = :department_id")
    Employee getOneEmployeeWithObjectCondition(DepartmentEmployee departmentEmployee);
}
```

### @JdbcPagination
```java
@JdbcRepository
public interface EmployeePaginationRepository {
    //Pagination
    //------------------------------------------------------
    @JdbcPagination(table = "employee")
    JdbcPage<Employee> paginationEmployeesWithoutParameter();

    @JdbcPagination(table = "employee", where = "id = :id")
    JdbcPage<Employee> paginationEmployeesWithCondition(Integer id);

    @JdbcPagination(table = "employee", where = "t.id between :from and :to", alias = "t")
    JdbcPage<Employee> paginationEmployeesWithConditionAndPageRequest(Integer from, Integer to, JdbcPageRequest pageRequest);

}
```

### @JdbcRawPagination
```java
@JdbcRepository
public interface EmployeeRawPaginationRepository {
    //Raw Pagination
    //------------------------------------------------------

    @JdbcRawCountQuery("select (:max - 50)")
    @JdbcRawPagination(value = "select * from sch_test.employee where id <= :max")
    JdbcPage<EmployeeProjection> paginationEmployeeProyectionWithConditionAndPageRequestAndCountQuery(Integer max, JdbcPageRequest pageRequest);

    @JdbcRawPagination(value = "select * from sch_test.employee where id between :from and :to")
    JdbcPage<Employee> paginationEmployeesWithConditionAndPageRequestRaw(Integer from, Integer to, JdbcPageRequest pageRequest);
}
```

### @JdbcUpdate
```java
@JdbcRepository
public interface EmployeeUpdateRepository {
    //Update
    //------------------------------------------------------
    @JdbcMapping(from = "firstName", to = "fname", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "lname", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "gen", type = Types.VARCHAR)
    @JdbcMapping(from = "id", to = "employeeId", type = Types.INTEGER)
    @JdbcUpdate(table = "employee", updateSets = {"first_name = :fname", "last_name = :lname", "gender = :gen"}, where = "id = :employeeId")
    int updateWithReturningInt(Employee employee);

    @JdbcUpdate(table = "employee", updateSets = {"first_name = :firstName", "last_name = :lastName", "gender = :gender"}, where = "id = :id", keyColumnNames = "id")
    KeyHolder updateWithReturningHolder(Employee employee);

    @JdbcFromTable(table = "employee", alias = "e")
    @JdbcJoinTable(table = "department", alias = "d", on = "1 = 1")
    @JdbcUpdate(table = "department_employee", alias = "de", updateSets = {"department_id = :deptName"},
            where = "(de.employee_id = e.id) and (de.department_id = d.id) and (e.first_name = :firstName and e.last_name = :lastName)",
            keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexTwoReturningHolder(String firstName, String lastName, String deptName);
}
```

### @JdbcRawUpdate
```java
@JdbcRepository
public interface EmployeeRawUpdateRepository {
    //Raw Update
    //------------------------------------------------------
    @JdbcMapping(from = "firstName", to = "fname", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "lname", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "gen", type = Types.VARCHAR)
    @JdbcMapping(from = "id", to = "employeeId", type = Types.INTEGER)
    @JdbcRawUpdate("update sch_test.employee set first_name = :fname, last_name = :lname, gender = :gen " +
            "where id = :employeeId")
    int updateWithReturningIntRaw(Employee employee);

    @JdbcRawUpdate(value = "update sch_test.department_employee de set department_id = :departmentId from sch_test.employee e " +
            "where de.employee_id = e.id and e.first_name = :firstName and e.last_name = :lastName",
            keyColumnNames = {"department_id", "employee_id"})
    KeyHolder updateComplexReturningHolderRaw(String firstName, String lastName, String departmentId);
}
```

### @JdbcInsert
```java
@JdbcRepository
public interface EmployeeInsertRepository {
    //Insert
    //------------------------------------------------------
    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"}, generatedKeyColumns = "id")
    KeyHolder insertEmployeeWithModelAndReturnKeyHolder(Employee employee);

    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"})
    int[] insertEmployeeBatchListAndReturnArray(List<Employee> employees);

    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"})
    int[] insertEmployeeBatchSetAndReturnArray(Set<Employee> employees);

    @JdbcMapping(from = "another_first_name", to = "first_name", type = Types.VARCHAR)
    @JdbcMapping(from = "another_gender", to = "gender", type = Types.VARCHAR)
    @JdbcInsert(table = "employee", columns = {"first_name", "last_name", "gender", "birth_date", "hire_date"})
    int[] insertEmployeeBatchMapAndReturnArray(Map<String, Object> map);
}
```

### @JdbcFunction
```java
@JdbcRepository
public interface EmployeeFunctionRepository {
    //Function
    //------------------------------------------------------
    @JdbcMapping(from = "numberOne", to = "var_number_one", type = Types.DOUBLE)
    @JdbcMapping(from = "numberTwo", to = "var_number_two", type = Types.DOUBLE)
    @JdbcFunction(name = "fn_sum_of_numbers_with_out_parameter", outParameters = @Parameter(value = "result", type = Types.DOUBLE))
    Double functionSumWithOutParameter(Double numberOne, Double numberTwo);

    @JdbcMapping(from = "numberOne", to = "var_number_one", type = Types.DOUBLE)
    @JdbcMapping(from = "numberTwo", to = "var_number_two", type = Types.DOUBLE)
    @JdbcFunction(name = "fn_multiplication_of_numbers_with_return")
    Double functionMultiplicationWithReturn(Double numberOne, Double numberTwo);

    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcFunction(name = "fn_employees_by_gender")
    List<Employee> functionEmployeesByGenderWithCursor(String gender);

    @JdbcMapping(from = "id", to = "var_id", type = Types.INTEGER)
    @JdbcFunction(name = "fn_employee_names_by_id_with_out_parameters", outParameters = {
            @Parameter(value = "out_first_name", type = Types.VARCHAR),
            @Parameter(value = "out_last_name", type = Types.VARCHAR)
    })
    Map<String, Object> functionEmployeeNamesWithOutParameters(Integer id);

    @JdbcMapping(from = "firstName", to = "var_first_name", type = Types.VARCHAR)
    @JdbcMapping(from = "lastName", to = "var_last_name", type = Types.VARCHAR)
    @JdbcMapping(from = "gender", to = "var_gender", type = Types.VARCHAR)
    @JdbcFunction(name = "fn_employees_by_object")
    Optional<Employee> functionEmployeesByObjectWithCursor(Employee employee);
}
```

### @JdbcProcedure
```java
@JdbcRepository
public interface EmployeeProcedureRepository {
    //Stored Procedure
    //------------------------------------------------------
    //POSTGRES_UnsupportedOperationException - Use @JdbcCall
}
```

### @JdbcExecute
```java
@JdbcRepository
public interface EmployeeExecuteRepository {
    //Execute
    //------------------------------------------------------

    @JdbcExecute("delete from sch_test.department where id = :id")
    int deleteDepartmentWithReturningInt(@JdbcParam("id") String id);

    @JdbcExecute("delete from sch_test.employee where id = :id")
    int deleteEmployeeWithReturningInt(Integer id);

    @JdbcExecute("delete from sch_test.employee where first_name in (:names)")
    int deleteEmployeesWithArrayAndReturningInt(String... names);

    @JdbcMapping(from = "id", to = "var_id", type = Types.VARCHAR)
    @JdbcMapping(from = "deptName", to = "var_dept_name", type = Types.VARCHAR)
    @JdbcExecute("call sch_test.sp_department_create(:var_id, :var_dept_name)")
    void executeCallDepartmentCreateWithoutResult(Department department);
}
```

### @JdbcCall
```java
@JdbcRepository
public interface EmployeeCallRepository {
    //Call
    //------------------------------------------------------

    @JdbcMapping(from = "id", to = "var_id", type = Types.VARCHAR)
    @JdbcMapping(from = "deptName", to = "var_dept_name", type = Types.VARCHAR)
    @JdbcCall(value = "sp_department_create", parameters = {":var_id", ":var_dept_name"})
    void callDepartmentCreate(Department department);
}
```


License
----

MIT
