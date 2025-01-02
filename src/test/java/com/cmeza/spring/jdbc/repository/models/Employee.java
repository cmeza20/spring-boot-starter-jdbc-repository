package com.cmeza.spring.jdbc.repository.models;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {
    private Integer id;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private Date hireDate;
    private Salary salary;
    private Title title;
}
