package com.cmeza.spring.jdbc.repository.models;

import lombok.Data;

import java.util.Date;

@Data
public class DepartmentManager {
    private Employee employee;
    private Department department;
    private Date fromDate;
    private Date toDate;
}
