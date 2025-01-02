package com.cmeza.spring.jdbc.repository.repositories.utils;

import com.cmeza.spring.jdbc.repository.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.parsers.ParsedJdbcSql;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

@UtilityClass
public final class JdbcNamedParameterUtils {
    private static final String[] START_SKIP = new String[]{"'", "\"", "--", "/*"};
    private static final String[] STOP_SKIP = new String[]{"'", "\"", "\n", "*/"};
    private static final String PARAMETER_SEPARATORS = "\"':&,;()|=+-*%/\\<>^]";
    private static final boolean[] separatorIndex = new boolean[128];

    static {
        for (char c : PARAMETER_SEPARATORS.toCharArray()) {
            separatorIndex[c] = true;
        }
    }

    public ParsedJdbcSql parseSqlStatement(String sql) {
        Assert.notNull(sql, "SQL must not be null");

        Set<String> namedParameters = new HashSet<>();
        StringBuilder sqlToUse = new StringBuilder(sql);
        List<ParameterHolder> parameterList = new ArrayList<>();

        char[] statement = sql.toCharArray();
        StatsParam statsParam = new StatsParam();

        while (statsParam.getI() < statement.length) {
            statsParam.setI(positionCommentsAndQuotes(statsParam.getI(), statement));

            if (statsParam.getI() >= statement.length) {
                break;
            }
            char c = statement[statsParam.getI()];

            evaluateParameters(c, statement,sql, statsParam, namedParameters, parameterList, sqlToUse);
            statsParam.iPlus();
        }
        ParsedJdbcSql parsedSql = new ParsedJdbcSql(sqlToUse.toString());
        for (ParameterHolder ph : parameterList) {
            parsedSql.addNamedParameter(ph.getParameterName(), ph.getStartIndex(), ph.getEndIndex());
        }
        parsedSql.setNamedParameterCount(statsParam.getNamedParameterCount());
        parsedSql.setUnnamedParameterCount(statsParam.getUnnamedParameterCount());
        parsedSql.setTotalParameterCount(statsParam.getTotalParameterCount());
        return parsedSql;
    }

    private int skipCommentsAndQuotes(char[] statement, int position) {
        for (int i = 0; i < START_SKIP.length; i++) {
            if (statement[position] == START_SKIP[i].charAt(0)) {
                boolean match = isMatchCommentsAndQuotes(statement, position, i);
                if (match) {
                    return getLengthFromMatchCommentsAndQuotes(statement, position, i);
                }
            }
        }
        return position;
    }

    private int addNewNamedParameter(Set<String> namedParameters, int namedParameterCount, String parameter) {
        if (!namedParameters.contains(parameter)) {
            namedParameters.add(parameter);
            namedParameterCount++;
        }
        return namedParameterCount;
    }

    private int addNamedParameter(List<ParameterHolder> parameterList, int totalParameterCount, int escapes, int i, int j, String parameter) {
        parameterList.add(new ParameterHolder(parameter, i - escapes, j - escapes));
        totalParameterCount++;
        return totalParameterCount;
    }

    private boolean isParameterSeparator(char c) {
        return (c < 128 && separatorIndex[c]) || Character.isWhitespace(c);
    }

    private boolean isMatchCommentsAndQuotes(char[] statement, int position, int i) {
        for (int j = 1; j < START_SKIP[i].length(); j++) {
            if (statement[position + j] != START_SKIP[i].charAt(j)) {
                return false;
            }
        }
        return true;
    }

    private int getLengthFromMatchCommentsAndQuotes(char[] statement, int position, int i) {
        int offset = START_SKIP[i].length();
        for (int m = position + offset; m < statement.length; m++) {
            if (statement[m] == STOP_SKIP[i].charAt(0)) {
                boolean endMatch = true;
                int endPos = m;
                for (int n = 1; n < STOP_SKIP[i].length(); n++) {
                    if (m + n >= statement.length) {
                        // last comment not closed properly
                        return statement.length;
                    }
                    if (statement[m + n] != STOP_SKIP[i].charAt(n)) {
                        endMatch = false;
                        break;
                    }
                    endPos = m + n;
                }
                if (endMatch) {
                    // found character sequence ending comment or quote
                    return endPos + 1;
                }
            }
        }
        // character sequence ending comment or quote not found
        return statement.length;
    }

    private void iterateParameterSources(SqlParameterSource[] paramSources, String paramName, StringBuilder actualSql) {
        for (SqlParameterSource paramSource : paramSources) {
            if (paramSource.hasValue(paramName)) {
                Object value = paramSource.getValue(paramName);
                evaluateParameterSourceValue(value, actualSql);
            } else {
                actualSql.append('?');
            }
        }
    }

    private void evaluateParameterSourceValue(Object value, StringBuilder actualSql) {
        if (value instanceof SqlParameterValue) {
            value = ((SqlParameterValue) value).getValue();
        }
        if (value instanceof Iterable) {
            Iterator<?> entryIter = ((Iterable<?>) value).iterator();
            int k = 0;
            while (entryIter.hasNext()) {
                if (k > 0) {
                    actualSql.append(", ");
                }
                k++;
                Object entryItem = entryIter.next();
                evaluateParameterSourceValueArray(entryItem, actualSql);
            }
        } else {
            actualSql.append('?');
        }
    }

    private void evaluateParameterSourceValueArray(Object entryItem, StringBuilder actualSql) {
        if (entryItem instanceof Object[]) {
            Object[] expressionList = (Object[]) entryItem;
            actualSql.append('(');
            for (int m = 0; m < expressionList.length; m++) {
                if (m > 0) {
                    actualSql.append(", ");
                }
                actualSql.append('?');
            }
            actualSql.append(')');
        } else {
            actualSql.append('?');
        }
    }

    public String substituteNamedParameters(ParsedJdbcSql parsedSql, @Nullable SqlParameterSource[] paramSources) {
        String originalSql = parsedSql.getOriginalSql();
        List<String> paramNames = parsedSql.getParameterNames();
        if (paramNames.isEmpty() || Objects.isNull(paramSources) || paramSources.length == 0) {
            return originalSql;
        }

        StringBuilder actualSql = new StringBuilder(originalSql.length());
        int lastIndex = 0;
        for (int i = 0; i < paramNames.size(); i++) {
            String paramName = paramNames.get(i);
            int[] indexes = parsedSql.getParameterIndexesArray(i);
            int startIndex = indexes[0];
            int endIndex = indexes[1];
            actualSql.append(originalSql, lastIndex, startIndex);
            iterateParameterSources(paramSources, paramName, actualSql);
            lastIndex = endIndex;
        }
        actualSql.append(originalSql, lastIndex, originalSql.length());

        return actualSql.toString().replaceAll("(([?])\\2{0})\\2+", "$1");
    }

    public List<SqlParameter> buildSqlParameterList(ParsedJdbcSql parsedSql, SqlParameterSource[] paramSources) {
        List<String> paramNames = parsedSql.getParameterNames();
        List<SqlParameter> params = new ArrayList<>(paramNames.size());
        for (String paramName : paramNames) {
            for (SqlParameterSource parameterSource : paramSources) {
                if (parameterSource.hasValue(paramName)) {
                    params.add(new SqlParameter(paramName, parameterSource.getSqlType(paramName), parameterSource.getTypeName(paramName)));
                }
            }
        }
        return params;
    }

    public Object[] buildValueArray(ParsedJdbcSql parsedSql, SqlParameterSource[] paramSources, @Nullable List<SqlParameter> declaredParams) {
        Object[] paramArray = new Object[parsedSql.getTotalParameterCount()];
        if (parsedSql.getNamedParameterCount() > 0 && parsedSql.getUnnamedParameterCount() > 0) {
            throw new InvalidDataAccessApiUsageException(
                    "Not allowed to mix named and traditional ? placeholders. You have " +
                            parsedSql.getNamedParameterCount() + " named parameter(s) and " +
                            parsedSql.getUnnamedParameterCount() + " traditional placeholder(s) in statement: " +
                            parsedSql.getOriginalSql());
        }
        List<String> paramNames = parsedSql.getParameterNames();
        for (int i = 0; i < paramNames.size(); i++) {
            String paramName = paramNames.get(i);
            try {
                SqlParameter param = findParameter(declaredParams, paramName, i);
                if (Objects.nonNull(paramSources)) {
                    buildParamArrayFromParameterSources(paramSources, paramName, paramArray, i, param);
                }

            } catch (IllegalArgumentException ex) {
                throw new InvalidDataAccessApiUsageException(
                        "No value supplied for the SQL parameter '" + paramName + "': " + ex.getMessage());
            }
        }
        return paramArray;
    }

    public Object[] buildValueArray(ParsedJdbcSql parsedSql, SqlParameterSource paramSource, @Nullable List<SqlParameter> declaredParams) {
        Object[] paramArray = new Object[parsedSql.getTotalParameterCount()];
        if (parsedSql.getNamedParameterCount() > 0 && parsedSql.getUnnamedParameterCount() > 0) {
            throw new InvalidDataAccessApiUsageException("Not allowed to mix named and traditional ? placeholders. You have " + parsedSql.getNamedParameterCount() + " named parameter(s) and " + parsedSql.getUnnamedParameterCount() + " traditional placeholder(s) in statement: " + parsedSql.getOriginalSql());
        } else {
            List<String> paramNames = parsedSql.getParameterNames();

            for (int i = 0; i < paramNames.size(); ++i) {
                String paramName = paramNames.get(i);

                try {
                    SqlParameter param = findParameter(declaredParams, paramName, i);
                    buildParamArrayFromParameterSource(paramSource, paramName, paramArray, i, param);

                } catch (IllegalArgumentException var9) {
                    throw new InvalidDataAccessApiUsageException("No value supplied for the SQL parameter '" + paramName + "': " + var9.getMessage());
                }
            }

            return paramArray;
        }
    }

    @Nullable
    private SqlParameter findParameter(@Nullable List<SqlParameter> declaredParams, String paramName, int paramIndex) {

        if (declaredParams != null) {
            // First pass: Look for named parameter match.
            for (SqlParameter declaredParam : declaredParams) {
                if (paramName.equals(declaredParam.getName())) {
                    return declaredParam;
                }
            }
            // Second pass: Look for parameter index match.
            if (paramIndex < declaredParams.size()) {
                SqlParameter declaredParam = declaredParams.get(paramIndex);
                // Only accept unnamed parameters for index matches.
                if (declaredParam.getName() == null) {
                    return declaredParam;
                }
            }
        }
        return null;
    }

    public <T> T singleResult(@Nullable Collection<T> results) {
        if (CollectionUtils.isEmpty(results)) {
            return null;
        } else {
            return results.iterator().next();
        }
    }

    public Parameter[] extractOutParameters(Parameter[] outParameters, NamingStrategy namingStrategy) {
        if (Objects.isNull(outParameters)) return outParameters;
        return Arrays.stream(outParameters).map(annotation -> {
            Map<String, Object> mapValues = AnnotationUtils.getAnnotationAttributes(annotation);
            mapValues.put("value", namingStrategy.parse((String)mapValues.get("value")));
            return JdbcUtils.updateAnnotation(annotation, mapValues);
        }).toArray(Parameter[]::new);
    }

    private int positionCommentsAndQuotes(int i, char[] statement) {
        while (i < statement.length) {
            int skipToPosition = skipCommentsAndQuotes(statement, i);
            if (i == skipToPosition) {
                break;
            } else {
                i = skipToPosition;
            }
        }
        return i;
    }


    private void evaluateParameters(char c, char[] statement, String sql, StatsParam statsParam, Set<String> namedParameters, List<ParameterHolder> parameterList, StringBuilder sqlToUse) {
        if (c == ':' || c == '&') {
            int j = statsParam.getI() + 1;
            if (c == ':' && j < statement.length && statement[j] == ':') {
                // Postgres-style "::" casting operator should be skipped
                statsParam.setI(statsParam.getI() + 2);
                return;
            }

            if (c == ':' && j < statement.length && statement[j] == '{') {
                // :{x} style parameter
                j = namedParameterIdentifierWithKeys(statement, j, statsParam, sql, namedParameters, parameterList);
            } else {
                j = namedParameterIdentifierWithBrackets(statement, j, statsParam, sql, namedParameters, parameterList);
            }
            statsParam.setI(j - 1);
        } else {
            nonParametersEvaluate(statement, c, sqlToUse, statsParam);
        }
    }

    private int namedParameterIdentifierWithKeys(char[] statement, int j, StatsParam statsParam, String sql, Set<String> namedParameters, List<ParameterHolder> parameterList) {
        // :{x} style parameter
        while (statement[j] != '}') {
            j++;
            if (j >= statement.length) {
                throw new InvalidDataAccessApiUsageException(
                        "Non-terminated named parameter declaration at position " + statsParam.getI() +
                                " in statement: " + sql);
            }
            if (statement[j] == ':' || statement[j] == '{') {
                throw new InvalidDataAccessApiUsageException(
                        "Parameter name contains invalid character '" + statement[j] +
                                "' at position " + statsParam.getI() + " in statement: " + sql);
            }
        }
        if (j - statsParam.getI() > 2) {
            String parameter = sql.substring(statsParam.getI() + 2, j);
            statsParam.setNamedParameterCount(addNewNamedParameter(namedParameters, statsParam.getNamedParameterCount(), parameter));
            statsParam.setTotalParameterCount(addNamedParameter(parameterList, statsParam.getTotalParameterCount(), statsParam.getEscapes(), statsParam.getI(), j + 1, parameter));
        }
        j++;
        return j;
    }

    private int namedParameterIdentifierWithBrackets(char[] statement, int j, StatsParam statsParam, String sql, Set<String> namedParameters, List<ParameterHolder> parameterList) {
        while (j < statement.length && !isParameterSeparator(statement[j])) {
            j++;
        }
        if (j - statsParam.getI() > 1) {
            String parameter = sql.substring(statsParam.getI() + 1, j);
            if (j < statement.length && statement[j] == ']' && parameter.contains("[")) {
                // preserve end bracket for index/key
                j++;
                parameter = sql.substring(statsParam.getI() + 1, j);
            }
            statsParam.setNamedParameterCount(addNewNamedParameter(namedParameters, statsParam.getNamedParameterCount(), parameter));
            statsParam.setTotalParameterCount(addNamedParameter(parameterList, statsParam.getTotalParameterCount(), statsParam.getEscapes(), statsParam.getI(), j, parameter));
        }
        return j;
    }

    private void nonParametersEvaluate(char[] statement, char c, StringBuilder sqlToUse, StatsParam statsParam) {
        if (c == '\\') {
            int j = statsParam.getI() + 1;
            if (j < statement.length && statement[j] == ':') {
                // escaped ":" should be skipped
                sqlToUse.deleteCharAt(statsParam.getI() - statsParam.getEscapes());
                statsParam.escapesPlus();
                statsParam.setI(statsParam.getI() + 2);
                return;
            }
        }
        if (c == '?') {
            int j = statsParam.getI() + 1;
            if (j < statement.length && (statement[j] == '?' || statement[j] == '|' || statement[j] == '&')) {
                // Postgres-style "??", "?|", "?&" operator should be skipped
                statsParam.setI(statsParam.getI() + 2);
                return;
            }
            statsParam.unnamedParameterCountPlus();
            statsParam.totalParameterCountPlus();
        }
    }

    private void buildParamArrayFromParameterSources(SqlParameterSource[] paramSources, String paramName, Object[] paramArray, int i, SqlParameter param) {
        for (SqlParameterSource paramSource : paramSources) {
            if (paramSource.hasValue(paramName)) {
                buildParamArrayFromParameterSource(paramSource, paramName, paramArray, i, param);
            }
        }
    }

    private void buildParamArrayFromParameterSource(SqlParameterSource paramSource, String paramName, Object[] paramArray, int i, SqlParameter param) {
        Object paramValue = paramSource.getValue(paramName);
        if (paramValue instanceof SqlParameterValue) {
            paramArray[i] = paramValue;
        } else {
            paramArray[i] = param != null ? new SqlParameterValue(param, paramValue) : SqlParameterSourceUtils.getTypedValue(paramSource, paramName);
        }
    }

    @Getter
    @RequiredArgsConstructor
    private class ParameterHolder {

        private final String parameterName;
        private final int startIndex;
        private final int endIndex;
    }

    @Data
    private class StatsParam {
        private int i;
        private int namedParameterCount;
        private int unnamedParameterCount;
        private int totalParameterCount;
        private int escapes;

        public void unnamedParameterCountPlus() {
            unnamedParameterCount++;
        }

        public void totalParameterCountPlus() {
            totalParameterCount++;
        }

        public void escapesPlus() {
            escapes++;
        }

        public void iPlus() {
            i++;
        }
    }
}
