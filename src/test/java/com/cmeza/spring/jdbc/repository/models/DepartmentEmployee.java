package com.cmeza.spring.jdbc.repository.models;

import lombok.Data;

import java.util.Date;

@Data
public class DepartmentEmployee {
    private Employee employee;
    private Department department;
    private Date fromDate;
    private Date toDate;
}
