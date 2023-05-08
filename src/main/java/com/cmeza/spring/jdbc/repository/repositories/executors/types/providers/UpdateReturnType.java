package com.cmeza.spring.jdbc.repository.repositories.executors.types.providers;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations.ExecuteImplementation;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcUpdateBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;

public enum UpdateReturnType implements ExecuteImplementation<JdbcUpdateBuilder> {
    VOID {
        @Override
        public Object execute(JdbcUpdateBuilder builder) {
            return builder.execute();
        }
    },
    INTEGER {
        @Override
        public Object execute(JdbcUpdateBuilder builder) {
            return builder.execute();
        }
    },
    KEY_HOLDER {
        @Override
        public Object execute(JdbcUpdateBuilder builder) {
            return builder.executeWithKey();
        }
    };

    public static UpdateReturnType from(ReturnType returnType) {
        return JdbcUtils.returnTypeFromEnumThrow(UpdateReturnType.class, returnType);
    }
}
