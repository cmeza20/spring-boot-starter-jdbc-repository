DROP PROCEDURE IF EXISTS sch_test.sp_employees_count_by_gender_with_out_parameter;;
DROP PROCEDURE IF EXISTS sch_test.sp_department_create;;


CREATE PROCEDURE sch_test.sp_employees_count_by_gender_with_out_parameter(
    var_gender varchar(1),
    OUT result int)
    select count(1) into result
    from sch_test.employee where gender = var_gender;
END PROCEDURE
;;


CREATE PROCEDURE sch_test.sp_department_create(
    var_id varchar(4),
    var_dept_name varchar(100))
INSERT INTO sch_test.department(id, dept_name)
VALUES (var_id, var_dept_name);
END PROCEDURE
;;
