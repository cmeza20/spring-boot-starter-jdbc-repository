package com.cmeza.spring.jdbc.repository.models;

import lombok.Data;

import java.util.Date;

@Data
public class Title {
    private Integer employeeId;
    private String title;
    private Date fromDate;
    private Date toDate;
}
