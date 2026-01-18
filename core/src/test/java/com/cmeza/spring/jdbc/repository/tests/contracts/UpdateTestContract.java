package com.cmeza.spring.jdbc.repository.tests.contracts;

public interface UpdateTestContract {
    void testUpdateWithReturningInt();

    void testUpdateWithReturningHolder();

    void testUpdateWithReturningHolderAndClassAttribute();

    void testUpdateComplexReturningHolder();

    void testUpdateComplexTwoReturningHolder();
}
