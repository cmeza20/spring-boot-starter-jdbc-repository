package com.cmeza.spring.jdbc.repository.projections;

import java.math.BigInteger;

public interface EmployeeProjection {
    BigInteger getId();

    String getFirstName();

    String getLastName();
}
