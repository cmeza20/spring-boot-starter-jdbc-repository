package com.cmeza.spring.jdbc.repository.repositories.executors.types.providers;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations.ExecuteImplementation;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcExecuteBuilder;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;

public enum ExecuteReturnType implements ExecuteImplementation<JdbcExecuteBuilder> {
    VOID {
        @Override
        public Object execute(JdbcExecuteBuilder builder) {
            return builder.execute();
        }
    },
    INTEGER {
        @Override
        public Object execute(JdbcExecuteBuilder builder) {
            return builder.execute();
        }
    },
    ARRAY {
        @Override
        public Object execute(JdbcExecuteBuilder builder) {
            return builder.executeBatch();
        }
    };

    public static ExecuteReturnType from(ReturnType returnType) {
        return JdbcUtils.returnTypeFromEnumThrow(ExecuteReturnType.class, returnType);
    }
}
