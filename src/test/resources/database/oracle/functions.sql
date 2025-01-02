CREATE OR REPLACE FUNCTION sch_test.fn_multiplication_of_numbers_with_return(
    var_number_one IN double precision,
    var_number_two IN double precision)
  RETURN double precision IS
begin
    return var_number_one * var_number_two;
end;
/
