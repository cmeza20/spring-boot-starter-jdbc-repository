CREATE OR REPLACE PROCEDURE sch_test.sp_department_create(
    IN var_id character varying,
    IN var_dept_name character varying)
LANGUAGE plpgsql AS
$BODY$
begin
    INSERT INTO sch_test.department(id, dept_name)
    VALUES (var_id, var_dept_name);
end;
$BODY$;;

