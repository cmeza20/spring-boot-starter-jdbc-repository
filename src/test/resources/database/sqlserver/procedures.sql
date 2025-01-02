
CREATE PROCEDURE sch_test.sp_employees_count_by_gender_with_out_parameter
    (@var_gender varchar(1), @res int output) as
begin
    set @res = (select count(1)
    from sch_test.employee where gender = @var_gender);
end;;





CREATE PROCEDURE sch_test.sp_employees_by_gender(@var_gender varchar(1))
as
begin
    select id, birth_date, first_name, last_name, gender, hire_date
    from sch_test.employee
    where gender = @var_gender;
end;;





CREATE PROCEDURE sch_test.sp_employees_by_id(@var_id integer) as
begin
    select id, birth_date, first_name, last_name, gender, hire_date
    from sch_test.employee
    where id = @var_id;
end;;




CREATE PROCEDURE sch_test.sp_department_create (@var_id varchar(4), @var_dept_name varchar(50))
AS
BEGIN
    INSERT INTO sch_test.department(id, dept_name)
    VALUES (@var_id, @var_dept_name);
END;;

