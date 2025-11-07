package com.cmeza.spring.jdbc.repository.projections;

public interface EmployeeAndTitleProjection {
    Integer getId();

    String getFirstName();

    String getLastName();

    TitleProjection getTitleObject();
}
