CREATE OR REPLACE FUNCTION sch_test.fn_sum_of_numbers_with_out_parameter(
    IN var_number_one double precision,
    IN var_number_two double precision,
    OUT result double precision)
  RETURNS double precision AS
$BODY$
begin
    result := var_number_one + var_number_two;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;;






CREATE OR REPLACE FUNCTION sch_test.fn_multiplication_of_numbers_with_return(
    IN var_number_one double precision,
    IN var_number_two double precision)
  RETURNS double precision AS
$BODY$
begin
return var_number_one * var_number_two;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;;






CREATE OR REPLACE FUNCTION sch_test.fn_employees_by_gender(
    IN var_gender character varying)
  RETURNS refcursor AS
$BODY$
       declare employeeList REFCURSOR;
begin
open employeeList for
select id, birth_date, first_name, last_name, gender, hire_date
from sch_test.employee
where gender = var_gender;
return employeeList;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;;






CREATE OR REPLACE FUNCTION sch_test.fn_employee_names_by_id_with_out_parameters(
    IN var_id integer,
    OUT out_first_name character varying,
    OUT out_last_name character varying)
   returns record AS
$BODY$
begin
select first_name, last_name
into out_first_name, out_last_name
from sch_test.employee
where id = var_id;

end;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;;






CREATE OR REPLACE FUNCTION sch_test.fn_employees_by_object(
    IN var_first_name character varying,
    IN var_last_name character varying,
    IN var_gender character varying)
  RETURNS refcursor AS
$BODY$
       declare employeeList REFCURSOR;
begin
open employeeList for
select id, birth_date, first_name, last_name, gender, hire_date
from sch_test.employee
where gender = var_gender and first_name = var_first_name and last_name = var_last_name;
return employeeList;
end;
$BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;;
