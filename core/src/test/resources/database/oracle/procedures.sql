CREATE PROCEDURE sch_test.sp_employees_count_by_gender_with_out_parameter(
    var_gender in varchar,
    result OUT int) IS
begin
select count(1) into result
from sch_test.employee where gender = var_gender;
end;
/






CREATE PROCEDURE sch_test.sp_employees_by_gender(var_gender IN varchar) IS
begin
select id, birth_date, first_name, last_name, gender, hire_date
from sch_test.employee
where gender = var_gender;
end;
/





CREATE PROCEDURE sch_test.sp_employees_by_id(var_id IN integer) is
begin
select id, birth_date, first_name, last_name, gender, hire_date
from sch_test.employee
where id = var_id;
end;
/




CREATE OR REPLACE PROCEDURE sch_test.sp_department_create(
    var_id IN varchar,
    var_dept_name IN varchar)
IS
begin
    INSERT INTO sch_test.department(id, dept_name)
    VALUES (var_id, var_dept_name);
end;
/

