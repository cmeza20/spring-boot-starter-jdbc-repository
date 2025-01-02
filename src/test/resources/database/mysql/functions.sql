CREATE FUNCTION sch_test.fn_sum_of_numbers_with_out_parameter(
    var_number_one double,
    var_number_two double)
    RETURNS double deterministic
begin
return var_number_one + var_number_two;
end;;




CREATE FUNCTION sch_test.fn_multiplication_of_numbers_with_return(
    var_number_one double,
    var_number_two double)
    RETURNS double deterministic
begin
return var_number_one * var_number_two;
end;;