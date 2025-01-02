# Change Log

## [1.0.0] - 2023-05-07
### Added
- @JdbcRepository annotation
- @JdbcQuery annotation
- @JdbcPagination annotation
- @JdbcUpdate annotation
- @JdbcBatchUpdate annotation
- @JdbcInsert annotation
- @JdbcFunction annotation
- @JdbcProcedure annotation
- @JdbcParam annotation
- @OutParameter annotation
- Custom JdbcRowMapper
- Custom JdbcRepositoryTemplate
- Jdbc projection support
- Custom JdbcRepositoryAware
- JdbcRepositoryTemplate multi tenant

## [1.0.1] - 2023-10-30
### Added
- Support for unnamed parameters

## [1.0.2] - 2023-11-06
### Added
- Support for projection without implementation

## [1.0.3] - 2023-11-08
### Added
- Global Naming Strategy
- Naming Strategy for JdbcFunction, JdbcInsert, JdbcProcedure and JdbcUpdate

## [1.0.4] - 2024-03-08
### Added
- Fix folder search in classpath
- Profiling in pom.xml

## [1.0.5] - 2024-03-28
### Added
- Add JdbcPropertyResolver method interceptor
- Add key implementation in JdbcBuilder
- Add RowMapper class in JdbcBuilder
### Fix
- Fix JdbcRoutineBuilder execute
- Fix logger in JdbcEnvironmentPostProcessor
- Fix Read resource with InputStreamReader

## [2.0.0] - 2025-01-02
### Added
- Add JdbcCall annotation
- Add JdbcExecute annotation
- Add JdbcRawPagination annotation
- Add JdbcRawQuery annotation
- Add JdbcRawUpdate annotation
- Add JdbcCountQuery annotation
- Add JdbcFromTable annotation
- Add JdbcJoinTable annotation
- Add JdbcMapping annotation
- Add JdbcRawCountQuery annotation
- Add JdbcProjectionRowMapper abstract class
- Add Integration tests (Postgres, Mysql, Oracle, SqlServer, Informix)
### Fix
- Fix parameters from objects
- Standalone JdbcRowMapper
### Changed
- @OutParameter is now @Parameter

[1.0.0]: https://github.com/cmeza20/spring-boot-starter-jdbc-repository/tree/1.0.0
[1.0.1]: https://github.com/cmeza20/spring-boot-starter-jdbc-repository/tree/1.0.1
[1.0.2]: https://github.com/cmeza20/spring-boot-starter-jdbc-repository/tree/1.0.2
[1.0.3]: https://github.com/cmeza20/spring-boot-starter-jdbc-repository/tree/1.0.3
[1.0.4]: https://github.com/cmeza20/spring-boot-starter-jdbc-repository/tree/1.0.4
[1.0.5]: https://github.com/cmeza20/spring-boot-starter-jdbc-repository/tree/1.0.5
[2.0.0]: https://github.com/cmeza20/spring-boot-starter-jdbc-repository/tree/2.0.0