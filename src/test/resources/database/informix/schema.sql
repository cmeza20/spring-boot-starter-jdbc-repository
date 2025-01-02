CREATE SCHEMA AUTHORIZATION sch_test;;

DROP TABLE IF EXISTS sch_test.title;;
DROP TABLE IF EXISTS sch_test.salary;;
DROP TABLE IF EXISTS sch_test.department_manager;;
DROP TABLE IF EXISTS sch_test.department_employee;;
DROP TABLE IF EXISTS sch_test.employee;;
DROP TABLE IF EXISTS sch_test.department;;

CREATE TABLE sch_test.department
(
    id        varchar(4)  NOT NULL primary key,
    dept_name varchar(40) NOT NULL
);;



CREATE TABLE sch_test.employee
(
    id         serial primary key,
    birth_date date        NOT NULL,
    first_name varchar(14) NOT NULL,
    last_name  varchar(16) NOT NULL,
    gender     varchar(1)  NOT NULL,
    hire_date  date        NOT NULL
);;


CREATE TABLE sch_test.department_employee
(
    employee_id   integer    NOT NULL references sch_test.employee (id),
    department_id varchar(4) NOT NULL references sch_test.department (id),
    from_date     date       NOT NULL,
    to_date       date       NOT NULL
);;

CREATE TABLE sch_test.department_manager
(
    employee_id   integer    NOT NULL references sch_test.employee (id),
    department_id varchar(4) NOT NULL references sch_test.department (id),
    from_date     date       NOT NULL,
    to_date       date       NOT NULL
);;

CREATE TABLE sch_test.salary
(
    employee_id integer NOT NULL references sch_test.employee (id),
    amount      bigint  NOT NULL,
    from_date   date    NOT NULL,
    to_date     date    NOT NULL
);;

CREATE TABLE sch_test.title
(
    employee_id integer     NOT NULL references sch_test.employee (id),
    title       varchar(50) NOT NULL,
    from_date   date        NOT NULL,
    to_date     date
);;