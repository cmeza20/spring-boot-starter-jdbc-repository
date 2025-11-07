package com.cmeza.spring.jdbc.repository.projections;

public interface EmployeeAndSalaryProjection extends EmployeeProjection {
    Double getAmount();
}
