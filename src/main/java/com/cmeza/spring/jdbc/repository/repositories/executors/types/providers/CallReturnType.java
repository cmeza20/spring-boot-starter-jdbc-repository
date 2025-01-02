package com.cmeza.spring.jdbc.repository.repositories.executors.types.providers;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations.ExecuteImplementation;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcCallBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;

public enum CallReturnType implements ExecuteImplementation<JdbcCallBuilder> {
    VOID {
        @Override
        public Object execute(JdbcCallBuilder builder) {
            builder.execute();
            return null;
        }
    };

    public static CallReturnType from(ReturnType returnType) {
        return JdbcUtils.returnTypeFromEnumThrow(CallReturnType.class, returnType);
    }
}
