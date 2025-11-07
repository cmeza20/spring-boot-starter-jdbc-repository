package com.cmeza.spring.jdbc.repository.mappers;

import com.cmeza.spring.jdbc.repository.models.Employee;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeAndSalaryRowMapper extends JdbcRowMapper<Employee> {

    private final SalaryRowMapper salaryRowMapper;

    protected EmployeeAndSalaryRowMapper(SalaryRowMapper salaryRowMapper) {
        super(Employee.class);
        this.salaryRowMapper = salaryRowMapper;
    }

    @Override
    protected void mapClass(ResultSet rs, Employee entity, int rowNumber) throws SQLException {
        entity.setSalary(salaryRowMapper.mapRow(rs, rowNumber));
    }

    @Override
    public Employee mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return super.mapRow(rs, rowNumber);
    }
}
