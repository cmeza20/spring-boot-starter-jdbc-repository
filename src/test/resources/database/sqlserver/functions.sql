CREATE FUNCTION sch_test.fn_multiplication_of_numbers_with_return(
    @var_number_one float,
    @var_number_two float)
    RETURNS float
begin
return @var_number_one * @var_number_two;
end;;