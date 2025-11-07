BEGIN
FOR cur_rec IN (SELECT object_name, object_type
                     FROM user_objects
                    WHERE object_type IN
                             ('TABLE',
                              'VIEW',
                              'PACKAGE',
                              'PROCEDURE',
                              'FUNCTION',
                              'SEQUENCE',
                              'TYPE',
                              'SYNONYM',
                              'MATERIALIZED VIEW'
                             ))
LOOP
    BEGIN
             IF cur_rec.object_type = 'TABLE'
             THEN
                EXECUTE IMMEDIATE    'DROP '
                                  || cur_rec.object_type
                                  || ' "'
                                  || cur_rec.object_name
                                  || '" CASCADE CONSTRAINTS';
    ELSE
                EXECUTE IMMEDIATE    'DROP '
                                  || cur_rec.object_type
                                  || ' "'
                                  || cur_rec.object_name
                                  || '"';
    END IF;
    EXCEPTION
             WHEN OTHERS
             THEN
                DBMS_OUTPUT.put_line (   'FAILED: DROP '
                                      || cur_rec.object_type
                                      || ' "'
                                      || cur_rec.object_name
                                      || '"'
                                     );
    END;
END LOOP;
execute immediate 'purge dba_recyclebin';
END;
/



CREATE TABLE sch_test.department
(
    id        varchar(4)  NOT NULL primary key,
    dept_name varchar(40) NOT NULL
)
/


CREATE TABLE sch_test.employee
(
    id         integer generated as identity primary key NOT NULL,
    birth_date date                                      NOT NULL,
    first_name varchar(14)                               NOT NULL,
    last_name  varchar(16)                               NOT NULL,
    gender     varchar(1)                                NOT NULL,
    hire_date  date                                      NOT NULL
)
/



CREATE TABLE sch_test.department_employee
(
    employee_id   integer    NOT NULL,
    department_id varchar(4) NOT NULL,
    from_date     date       NOT NULL,
    to_date       date       NOT NULL,
    CONSTRAINT department_employee_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id),
    CONSTRAINT department_employee_department_id_fkey FOREIGN KEY (department_id)
        REFERENCES sch_test.department (id)
)
/

CREATE TABLE sch_test.department_manager
(
    employee_id   integer    NOT NULL,
    department_id varchar(4) NOT NULL,
    from_date     date       NOT NULL,
    to_date       date       NOT NULL,
    CONSTRAINT department_manager_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id),
    CONSTRAINT department_manager_department_id_fkey FOREIGN KEY (department_id)
        REFERENCES sch_test.department (id)
)
/

CREATE TABLE sch_test.salary
(
    employee_id integer NOT NULL,
    amount      integer NOT NULL,
    from_date   date    NOT NULL,
    to_date     date    NOT NULL,
    CONSTRAINT salary_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id)
)
/

CREATE TABLE sch_test.title
(
    employee_id integer     NOT NULL,
    title       varchar(50) NOT NULL,
    from_date   date        NOT NULL,
    to_date     date,
    CONSTRAINT title_employee_id_fkey FOREIGN KEY (employee_id)
        REFERENCES sch_test.employee (id)
)
/

commit
/