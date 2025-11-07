DECLARE @dababase nvarchar(200) = 'db_jdbc_repository';
DECLARE @sql nvarchar(200);
DECLARE drop_cursor CURSOR FOR
(
    select
        'ALTER TABLE ' + tc.table_schema + '.' + tc.table_name + ' DROP CONSTRAINT ' + tc.constraint_name + ';'
    from
        INFORMATION_SCHEMA.TABLES t
       ,INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
    where
        t.table_name = tc.table_name
      and tc.constraint_name not like '%_pk'
      and tc.constraint_name not like 'pk_%'
      and t.table_catalog=@dababase
) UNION (
    select
        'DROP TABLE ' + t.table_schema + '.' + t.table_name + ';'
    from
        INFORMATION_SCHEMA.TABLES t
    where
        t.table_catalog=@dababase
) UNION(
    SELECT  'DROP ' + r.ROUTINE_TYPE + ' ' + r.routine_schema + '.' + r.routine_name + ';'
    FROM INFORMATION_SCHEMA.ROUTINES r
    WHERE r.ROUTINE_CATALOG = @dababase
)
OPEN drop_cursor;
FETCH NEXT FROM drop_cursor INTO @sql;
While (@@FETCH_STATUS = 0)
BEGIN
    PRINT @sql
    exec    sp_executesql @sql
    FETCH NEXT FROM drop_cursor INTO @sql;
END
CLOSE drop_cursor;
DEALLOCATE drop_cursor;


DROP SCHEMA if exists sch_test;;
CREATE SCHEMA sch_test;;

CREATE TABLE sch_test.department
(
    id        character(4)          NOT NULL primary key,
    dept_name character varying(40) NOT NULL
);;

CREATE TABLE sch_test.employee
(
    id  INTEGER  NOT NULL primary key IDENTITY(1,1),
    birth_date date                     NOT NULL,
    first_name character varying(14)    NOT NULL,
    last_name  character varying(16)    NOT NULL,
    gender     character varying(1) NOT NULL,
    hire_date  date                     NOT NULL
);;

CREATE TABLE sch_test.department_employee
(
    employee_id   INTEGER       NOT NULL,
    department_id character(4) NOT NULL,
    from_date     date         NOT NULL,
    to_date       date         NOT NULL,
    CONSTRAINT department_employee_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id),
    CONSTRAINT department_employee_department_id_fkey FOREIGN KEY (department_id)
        REFERENCES sch_test.department (id)
);;

CREATE TABLE sch_test.department_manager
(
    employee_id   INTEGER       NOT NULL,
    department_id character(4) NOT NULL,
    from_date     date         NOT NULL,
    to_date       date         NOT NULL,
    CONSTRAINT department_manager_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id),
    CONSTRAINT department_manager_department_id_fkey FOREIGN KEY (department_id)
        REFERENCES sch_test.department (id)
);;

CREATE TABLE sch_test.salary
(
    employee_id INTEGER NOT NULL,
    amount      bigint NOT NULL,
    from_date   date   NOT NULL,
    to_date     date   NOT NULL,
    CONSTRAINT salary_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id)
);;

CREATE TABLE sch_test.title
(
    employee_id INTEGER                NOT NULL,
    title       character varying(50) NOT NULL,
    from_date   date                  NOT NULL,
    to_date     date,
    CONSTRAINT title_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id)
);;