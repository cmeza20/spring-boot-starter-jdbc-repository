package com.cmeza.spring.jdbc.repository.models;

import lombok.Data;

import java.util.Date;

@Data
public class Salary {
    private Integer employeeId;
    private Double amount;
    private Date fromDate;
    private Date toDate;
}
