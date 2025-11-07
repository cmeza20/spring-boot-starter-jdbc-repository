package com.cmeza.spring.jdbc.repository.tests.contracts;

public interface UpdateTestContract {
    void testUpdateWithReturningInt();

    void testUpdateWithReturningHolder();

    void testUpdateComplexReturningHolder();

    void testUpdateComplexTwoReturningHolder();
}
