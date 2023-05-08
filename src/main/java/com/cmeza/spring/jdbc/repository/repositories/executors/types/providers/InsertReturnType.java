package com.cmeza.spring.jdbc.repository.repositories.executors.types.providers;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations.ExecuteImplementation;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcInsertBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;

public enum InsertReturnType implements ExecuteImplementation<JdbcInsertBuilder> {
    INTEGER {
        @Override
        public Object execute(JdbcInsertBuilder builder) {
            return builder.execute();
        }
    },
    KEY_HOLDER {
        public Object execute(JdbcInsertBuilder builder) {
            return builder.executeAndReturnKeyHolder();
        }
    },
    ARRAY {
        public Object execute(JdbcInsertBuilder builder) {
            return builder.executeBatch();
        }
    };

    public static InsertReturnType from(ReturnType returnType) {
        return JdbcUtils.returnTypeFromEnumThrow(InsertReturnType.class, returnType);
    }
}
