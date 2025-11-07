package com.cmeza.spring.jdbc.repository.repositories.template.parsers;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ParsedJdbcSql {
    private final String originalSql;
    private final List<String> parameterNames = new LinkedList<>();
    private final List<int[]> parameterIndexes = new LinkedList<>();
    private int namedParameterCount;
    private int unnamedParameterCount;
    private int totalParameterCount;

    public ParsedJdbcSql(String originalSql) {
        this.originalSql = originalSql;
    }

    public void addNamedParameter(String parameterName, int startIndex, int endIndex) {
        this.parameterNames.add(parameterName);
        this.parameterIndexes.add(new int[]{startIndex, endIndex});
    }

    @Override
    public String toString() {
        return this.originalSql;
    }

    public int[] getParameterIndexesArray(int parameterPosition) {
        return this.parameterIndexes.get(parameterPosition);
    }
}
