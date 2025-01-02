package com.cmeza.spring.jdbc.repository.mappers;

import com.cmeza.spring.jdbc.repository.models.Employee;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeRowMapper extends JdbcRowMapper<Employee> {
    protected EmployeeRowMapper() {
        super(Employee.class);
    }

    @Override
    public Employee mapRow(ResultSet rs, int rowNumber) throws SQLException {
        return super.mapRow(rs, rowNumber);
    }
}
