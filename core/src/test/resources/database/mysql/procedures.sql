CREATE PROCEDURE sch_test.sp_employees_count_by_gender_with_out_parameter(
    IN var_gender varchar(1),
    OUT result int)
begin
    select count(1) into result
    from sch_test.employee where gender = var_gender;
end;;






CREATE PROCEDURE sch_test.sp_employees_by_gender(IN var_gender varchar(1))
begin
    select id, birth_date, first_name, last_name, gender, hire_date
    from sch_test.employee
    where gender = var_gender;
end;;





CREATE PROCEDURE sch_test.sp_employees_by_id(IN var_id integer)
begin
    select id, birth_date, first_name, last_name, gender, hire_date
    from sch_test.employee
    where id = var_id;
end;;




CREATE PROCEDURE sch_test.sp_department_create(
    IN var_id varchar(5),
    IN var_dept_name varchar(20))
begin
    INSERT INTO sch_test.department(id, dept_name)
    VALUES (var_id, var_dept_name);
end;;
