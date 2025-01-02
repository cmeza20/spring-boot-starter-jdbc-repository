DROP SCHEMA IF EXISTS sch_test CASCADE;;
CREATE SCHEMA sch_test;;

CREATE TABLE sch_test.department
(
    id        character(4)          NOT NULL primary key,
    dept_name character varying(40) NOT NULL
);;

CREATE SEQUENCE sch_test.employee_id_seq;;

CREATE TABLE sch_test.employee
(
    id         integer                NOT NULL DEFAULT nextval('sch_test.employee_id_seq') primary key,
    birth_date date                     NOT NULL,
    first_name character varying(14)    NOT NULL,
    last_name  character varying(16)    NOT NULL,
    gender     character varying(1) NOT NULL,
    hire_date  date                     NOT NULL
);;

ALTER SEQUENCE sch_test.employee_id_seq OWNED BY sch_test.employee.id;;

CREATE TABLE sch_test.department_employee
(
    employee_id   integer       NOT NULL,
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
    employee_id   integer       NOT NULL,
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
    employee_id integer NOT NULL,
    amount      bigint NOT NULL,
    from_date   date   NOT NULL,
    to_date     date   NOT NULL,
    CONSTRAINT salary_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id)
);;

CREATE TABLE sch_test.title
(
    employee_id integer                NOT NULL,
    title       character varying(50) NOT NULL,
    from_date   date                  NOT NULL,
    to_date     date,
    CONSTRAINT title_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id)
);;