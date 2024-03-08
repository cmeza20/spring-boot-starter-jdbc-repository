# Spring Boot Starter JDBC Repository [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.cmeza/spring-boot-starter-jdbc-repository/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.cmeza/spring-boot-starter-jdbc-repository)

Jdbc template repositories, inspired by Spring data Jpa

### Wiki ##

* [Get Started](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/Get-Started)
* [Properties](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/Properties)
* [@JdbcRepository annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcRepository-annotation)

***

### Methods
* [@JdbcQuery annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcQuery-annotation)
* [@JdbcPagination annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcPagination-annotation)
* [@JdbcUpdate annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcUpdate-annotation)
* [@JdbcBatchUpdate annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcBatchUpdate-annotation)
* [@JdbcInsert annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcInsert-annotation)
* [@JdbcFunction annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcFunction-annotation)
* [@JdbcProcedure annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcProcedure-annotation)

***

### Parameters
* [@JdbcParam annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@JdbcParam-annotation)
* [@OutParameter annotation](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/@OutParameter-annotation)

***
### Mappers
* [JdbcRowMapper interface](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRowMapper)
* [Support for projections](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/projections-support)

***
### Advanced
* [Custom JdbcRepositoryAware](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRepositoryAware)
* [Properties Placeholder](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/Properties-Placeholder)
* [JdbcRepositoryTemplate multi tenant](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRepositoryTemplate-multi-tenant)
* [JdbcRepositoryTemplate manual execute](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/JdbcRepositoryTemplate)
* [NamingStrategy](https://github.com/cmeza20/spring-boot-starter-jdbc-repository/wiki/NamingStrategy)


## Maven Integration ##

```xml

<dependency>
    <groupId>com.cmeza</groupId>
    <artifactId>spring-boot-starter-jdbc-repository</artifactId>
    <version>1.0.4</version>
</dependency>
```

### Example

```java
import com.cmeza.spring.jdbc.repository.annotations.methods.JdbcUpdate;
import com.cmeza.spring.jdbc.repository.naming.CamelToSnakeCaseNamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.SnakeToCamelCaseNamingStrategy;

@JdbcRepository
public interface UserRepository {

    //Query
    //------------------------------------------------------
    @JdbcQuery(value = "select * from user")
    List<User> getAll();

    @JdbcQuery(value = "select * from user where id = :id")
    User getOne(Long id);


    //Pagination
    //------------------------------------------------------
    @JdbcPagination(value = "select * from user")
    JdbcPage<User> getAll(JdbcPageRequest pageRequest);

    @JdbcPagination(value = "select * from user", countQuery = "select count(1) from user")
    JdbcPage<User> getAll2(JdbcPageRequest pageRequest);


    //Update
    //------------------------------------------------------
    @JdbcUpdate(value = "update user set name = :name where id = :id")
    int userUpdate(String name, Long id);

    @JdbcUpdate(value = "insert into user(name, lastname) values (:name, :lastname)", keyColumnNames = "id")
    KeyHolder userInsert(String name, String lastname);

    @JdbcUpdate(value = "truncate user")
    void usersTruncate();

    @JdbcUpdate(value = "insert into user(name, lastname) values (:name, :lastname)", keyColumnNames = "userId", columnsNamingStrategy = CamelToSnakeCaseNamingStrategy.class)
    int insertUserSnakeCase(Long id);


    //Batch Update
    //------------------------------------------------------
    @JdbcBatchUpdate(value = "insert into user(name, lastname) values (:name, :lastname)")
    int[] usersInsert(List<User> temps);

    @JdbcBatchUpdate(value = "delete from user where id in :ids")
    void deleteUsers(List<Integer> ids);


    //Insert
    //------------------------------------------------------
    @JdbcInsert(table = "user", columns = {"name", "lastname"}, generatedKeyColumns = "id")
    int userInsert(String name, String lastname);

    @JdbcInsert(table = "user", columns = {"name", "lastname"}, generatedKeyColumns = "id")
    Number userInsert3(User user);

    @JdbcInsert(table = "user", columns = {"name", "lastname"}, generatedKeyColumns = "id")
    int[] userInsert4(List<User> users);

    @JdbcInsert(table = "user", columns = {"name", "lastname"}, generatedKeyColumns = "id")
    KeyHolder userInsert5(User user);

    @JdbcInsert(table = "user", columns = {"user_name", "last_name"}, generatedKeyColumns = "user_id", columnsNamingStrategy = SnakeToCamelCaseNamingStrategy.class)
    int userInsertCamelCase(String name, String lastname);


    //Function
    //------------------------------------------------------
    @JdbcFunction(name = "fn_users_get_all")
    Set<User> userGetAll();

    @JdbcFunction(name = "fn_users_get_one")
    Optional<User> userGetOne(@JdbcParam(value = "var_id", type = Types.INTEGER) Long id);

    @JdbcFunction(name = "fn_users_bin_process", outParameters = {
            @OutParameter(value = "process1", type = Types.VARCHAR),
            @OutParameter(value = "process2", type = Types.VARCHAR)
    })
    Map<String, Object> userBinProcess();

    @JdbcFunction(name = "fn_users_uuid", outParameters = @OutParameter(value = "result", type = Types.VARCHAR))
    String userUUID();

    @JdbcFunction(name = "fn_users_bin_process_snake_case", outParameters = {
            @OutParameter(value = "processOne", type = Types.VARCHAR),
            @OutParameter(value = "processTwo", type = Types.VARCHAR)
    }, inParameterNames = "simpleParam", parametersNamingStrategy = CamelToSnakeCaseNamingStrategy.class)
    Map<String, Object> userBinProcessSnakeCase();

    //Stored Procedure
    //------------------------------------------------------
    @JdbcProcedure(name = "sp_users_all")
    List<User> usersList();

    @JdbcProcedure(name = "sp_users_one")
    Optional<User> usersOne(@JdbcParam(value = "var_id") Long id);

    @JdbcProcedure(name = "sp_users_created_at", outParameters = @OutParameter(value = "created_at", type = Types.TIMESTAMP, order = 2))
    Date userCreatedAt(@JdbcParam(value = "var_id") Long id);

    @JdbcProcedure(name = "sp_users_created_at_and_updated_at", outParameters = {
            @OutParameter(value = "created_at", type = Types.TIMESTAMP, order = 2),
            @OutParameter(value = "updated_at", type = Types.TIMESTAMP, order = 3)
    })
    Map<String, Object> userCreatedAtAndUpdatedAt(@JdbcParam(value = "var_id") Long id);

    @JdbcProcedure(name = "sp_users_created_at_and_updated_at_camel_case", outParameters = {
            @OutParameter(value = "created_at", type = Types.TIMESTAMP, order = 2),
            @OutParameter(value = "updated_at", type = Types.TIMESTAMP, order = 3)
    }, parametersNamingStrategy = SnakeToCamelCaseNamingStrategy.class)
    Map<String, Object> userCreatedAtAndUpdatedAtCamelCase(@JdbcParam(value = "var_id") Long id);

    //Projection support
    //------------------------------------------------------
    @JdbcQuery(value = "select * from user where id = :id")
    MinimalUser getMinimalUser(Long id);
}
```

License
----

MIT
