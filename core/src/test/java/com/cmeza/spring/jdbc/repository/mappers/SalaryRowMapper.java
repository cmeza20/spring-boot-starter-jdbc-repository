package com.cmeza.spring.jdbc.repository.mappers;

import com.cmeza.spring.jdbc.repository.models.Salary;
import org.springframework.stereotype.Component;

@Component
public class SalaryRowMapper extends JdbcRowMapper<Salary> {
    protected SalaryRowMapper() {
        super(Salary.class);
    }
}
