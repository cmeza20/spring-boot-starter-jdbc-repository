CREATE TABLE test.department
(
    id        varchar(4)  NOT NULL primary key,
    dept_name varchar(40) NOT NULL
)
/


CREATE TABLE test.employee
(
    id         integer generated as identity primary key NOT NULL,
    birth_date date                                      NOT NULL,
    first_name varchar(14)                               NOT NULL,
    last_name  varchar(16)                               NOT NULL,
    gender     varchar(1)                                NOT NULL,
    hire_date  date                                      NOT NULL
)
/



CREATE TABLE test.department_employee
(
    employee_id   integer    NOT NULL,
    department_id varchar(4) NOT NULL,
    from_date     date       NOT NULL,
    to_date       date       NOT NULL,
    CONSTRAINT department_employee_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES test.employee (id),
    CONSTRAINT department_employee_department_id_fkey FOREIGN KEY (department_id)
        REFERENCES test.department (id)
)
/

CREATE TABLE test.department_manager
(
    employee_id   integer    NOT NULL,
    department_id varchar(4) NOT NULL,
    from_date     date       NOT NULL,
    to_date       date       NOT NULL,
    CONSTRAINT department_manager_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES test.employee (id),
    CONSTRAINT department_manager_department_id_fkey FOREIGN KEY (department_id)
        REFERENCES test.department (id)
)
/

CREATE TABLE test.salary
(
    employee_id integer NOT NULL,
    amount      integer NOT NULL,
    from_date   date    NOT NULL,
    to_date     date    NOT NULL,
    CONSTRAINT salary_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES test.employee (id)
)
/

CREATE TABLE test.title
(
    employee_id integer     NOT NULL,
    title       varchar(50) NOT NULL,
    from_date   date        NOT NULL,
    to_date     date,
    CONSTRAINT title_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES test.employee (id)
)
/

commit
/