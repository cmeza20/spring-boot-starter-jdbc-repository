package com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcNamedParameterUtils;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public abstract class AbstractRoutineBuilder extends AbstractJdbcBuilder<JdbcRoutineBuilder> implements JdbcRoutineBuilder {

    private static final String ROUTINE_SUPPORT_EXCEPTION_MESSAGE = "Routine does not support parameters in batch";
    private static final String OUTPARAMETER_NAME = "@OUT_RESULT";
    private static final String RESULTSET_NAME = "@ROWMAP_RESULT";
    protected final String routineName;
    private final List<Map.Entry<Direction, SqlParameter>> parameters = new LinkedList<>();
    private final SimpleJdbcCall simpleJdbcCall;
    private boolean hasOutParameter;

    protected AbstractRoutineBuilder(String routineName, Impl impl) {
        super(impl);
        this.routineName = routineName;
        this.simpleJdbcCall = new SimpleJdbcCall(impl.getJdbcRepositoryTemplate().getJdbcTemplate());
        this.simpleJdbcCall.setNamedBinding(false);
        this.configureSimpleJdbcCall(simpleJdbcCall);
    }

    protected abstract void printExtrasRoutine(Logger logger);

    protected abstract void configureSimpleJdbcCall(SimpleJdbcCall simpleJdbcCall);

    protected abstract void printParameter(Logger logger, Direction direction, String name, String typeName);

    @Override
    public void printExtras(Logger logger) {
        String catalog = simpleJdbcCall.getCatalogName();
        String schema = simpleJdbcCall.getSchemaName();
        Set<String> inParameterNames = simpleJdbcCall.getInParameterNames();
        if (Objects.nonNull(catalog) && !catalog.isEmpty()) {
            logger.info("| Catalog: {}", catalog);
        }
        if (Objects.nonNull(schema) && !schema.isEmpty()) {
            logger.info("| Schema: {}", simpleJdbcCall.getSchemaName());
        }
        this.printExtrasRoutine(logger);
        if (!inParameterNames.isEmpty()) {
            logger.info("| inParameterNames: {}", inParameterNames);
        }

        for (Map.Entry<Direction, SqlParameter> entry : parameters) {
            printParameter(logger, entry.getKey(), entry.getValue().getName(), JdbcUtils.getType(entry.getValue().getSqlType()));
        }
    }

    @Override
    public <T> T execute(Class<T> returnType) {
        int type = StatementCreatorUtils.javaTypeToSqlParameterType(returnType);
        if (type != SqlTypeValue.TYPE_UNKNOWN) {
            Map.Entry<String, SimpleJdbcCall> callEntry = prepareRoutineJdbcCall(returnType);
            return executeInternal(callEntry, returnType);
        }
        return JdbcNamedParameterUtils.singleResult(executeListInternal(returnType));

    }

    @Override
    public <T> List<T> executeList() {
        Map.Entry<String, SimpleJdbcCall> callEntry = prepareRoutineJdbcCall(List.class);
        return executeListInternal(callEntry);
    }

    @Override
    public <T> List<T> executeList(Class<T> returnType) {
        return executeListInternal(returnType);
    }

    @Override
    public <T> Set<T> executeSet() {
        Map.Entry<String, SimpleJdbcCall> callEntry = prepareRoutineJdbcCall(List.class);
        return new HashSet<>(this.executeListInternal(callEntry));
    }

    @Override
    public <T> Set<T> executeSet(Class<T> returnType) {
        return new HashSet<>(this.executeListInternal(returnType));
    }

    @Override
    public <T> Stream<T> executeStream() {
        Map.Entry<String, SimpleJdbcCall> callEntry = prepareRoutineJdbcCall(List.class);
        return (Stream<T>) Stream.of(this.executeListInternal(callEntry));
    }

    @Override
    public <T> Stream<T> executeStream(Class<T> returnType) {
        return (Stream<T>) Stream.of(this.executeListInternal(returnType));
    }

    @Override
    public <T> Optional<T> executeOptional() {
        Map.Entry<String, SimpleJdbcCall> callEntry = prepareRoutineJdbcCall(List.class);
        return Optional.ofNullable(JdbcNamedParameterUtils.singleResult(executeListInternal(callEntry)));
    }

    @Override
    public <T> Optional<T> executeOptional(Class<T> returnType) {
        return Optional.ofNullable(JdbcNamedParameterUtils.singleResult(executeListInternal(returnType)));
    }

    @Override
    public <T> T[] executeArray(Class<T> returnType) {
        List<T> ts = this.executeListInternal(returnType);
        T[] arr = (T[]) Array.newInstance(returnType, ts.size());
        return ts.toArray(arr);
    }

    @Override
    public Map<String, Object> executeMap() {
        SimpleJdbcCall call = prepareRoutineJdbcCall(Object.class).getValue();
        return call.execute(getMergedSqlParameterSource());
    }

    @Override
    public JdbcRoutineBuilder withSchema(String schema) {
        if (Objects.nonNull(schema) && !schema.isEmpty()) {
            this.simpleJdbcCall.withSchemaName(schema);
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withCatalog(String catalog) {
        if (Objects.nonNull(catalog) && !catalog.isEmpty()) {
            this.simpleJdbcCall.withCatalogName(catalog);
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withInParameterNames(String... inParameterNames) {
        if (Objects.nonNull(inParameterNames) && inParameterNames.length > 0) {
            this.simpleJdbcCall.useInParameterNames(inParameterNames);
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withOutParameters(SqlOutParameter... outParameters) {
        if (Objects.nonNull(outParameters) && outParameters.length > 0) {
            this.hasOutParameter = true;
            Arrays.stream(outParameters).forEach(out -> parameters.add(new AbstractMap.SimpleEntry<>(Direction.OUT, out)));
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withOutParameter(String parameter, int sqlType) {
        if (Objects.nonNull(parameter)) {
            this.hasOutParameter = true;
            SqlOutParameter outParameter = new SqlOutParameter(parameter, sqlType);
            parameters.add(new AbstractMap.SimpleEntry<>(Direction.OUT, outParameter));
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withParameter(String parameterName, Object parameterValue) {
        super.withParameter(parameterName, parameterValue);
        if (Objects.nonNull(parameterName)) {
            int type = StatementCreatorUtils.javaTypeToSqlParameterType(Objects.nonNull(parameterValue) ? parameterValue.getClass() : null);
            SqlParameter parameter = new SqlParameter(parameterName, type);
            parameters.add(new AbstractMap.SimpleEntry<>(Direction.IN, parameter));
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withParameter(String parameterName, Object parameterValue, int sqlType) {
        if (sqlType == 0) {
            return withParameter(parameterName, parameterValue);
        }
        super.withParameter(parameterName, parameterValue, sqlType);
        if (Objects.nonNull(parameterName)) {
            SqlParameter parameter = new SqlParameter(parameterName, sqlType);
            parameters.add(new AbstractMap.SimpleEntry<>(Direction.IN, parameter));
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withParameterMap(Map<?, ?> objects) {
        super.withParameterMap(objects);
        if (Objects.nonNull(objects)) {
            objects.forEach((key, value) -> {
                int type = StatementCreatorUtils.javaTypeToSqlParameterType(Objects.nonNull(value) ? value.getClass() : null);
                SqlParameter parameter = new SqlParameter((String) key, type);
                parameters.add(new AbstractMap.SimpleEntry<>(Direction.IN, parameter));
            });
        }
        return this;
    }

    @Override
    public JdbcRoutineBuilder withParameterList(List<?> objects) {
        throw new UnsupportedOperationException(ROUTINE_SUPPORT_EXCEPTION_MESSAGE);
    }

    @Override
    public JdbcRoutineBuilder withParameterSet(Set<?> objects) {
        throw new UnsupportedOperationException(ROUTINE_SUPPORT_EXCEPTION_MESSAGE);
    }

    @Override
    public JdbcRoutineBuilder withAccessCallParameterMetaData(boolean value) {
        this.simpleJdbcCall.setAccessCallParameterMetaData(value);
        return this;
    }

    private <T> List<T> executeListInternal(Map.Entry<String, SimpleJdbcCall> callEntry) {
        this.rowMapperRequired();
        return this.simpleJdbcCallExecute(getMergedSqlParameterSource(), List.class, callEntry);
    }

    private <T> T executeInternal(Map.Entry<String, SimpleJdbcCall> callEntry, @NonNull Class<T> resultType) {
        this.resultTypeRequired(resultType);
        return this.simpleJdbcCallExecute(getMergedSqlParameterSource(), resultType, callEntry);
    }

    private <T> List<T> executeListInternal(@NonNull Class<T> resultType) {
        this.resultTypeRequired(resultType);
        this.createRowMapperIfnotExists(resultType);
        Map.Entry<String, SimpleJdbcCall> callEntry = prepareRoutineJdbcCall(resultType);
        return executeListInternal(callEntry);
    }

    private <T> T simpleJdbcCallExecute(SqlParameterSource parameterSource, Class<T> resultType, Map.Entry<String, SimpleJdbcCall> callEntry) {
        return execute(() -> {
            if (Objects.nonNull(callEntry.getKey())) {
                return (T) callEntry.getValue().execute(parameterSource).get(callEntry.getKey());
            }

            return callEntry.getValue().executeFunction(resultType, parameterSource);
        });
    }

    private <T> Map.Entry<String, SimpleJdbcCall> prepareRoutineJdbcCall(Class<T> resultType) {
        Assert.isTrue(getBeanParameterSources().size() <= 1, ROUTINE_SUPPORT_EXCEPTION_MESSAGE);

        getBeanParameterSources()
                .forEach(bean -> Arrays.stream(Objects.requireNonNull(bean.getParameterNames()))
                        .filter(name -> !name.equalsIgnoreCase("class"))
                        .forEach(name -> {
                            SqlParameter sqlParameter = new SqlParameter(name, bean.getSqlType(name));
                            parameters.add(new AbstractMap.SimpleEntry<>(Direction.IN, sqlParameter));
                        }));

        RowMapper<?> rowMapper = getRowMapper();
        boolean hasRowMapper = Objects.nonNull(rowMapper) && !(rowMapper instanceof SingleColumnRowMapper) && !(rowMapper instanceof ColumnMapRowMapper);
        int type = StatementCreatorUtils.javaTypeToSqlParameterType(resultType);

        String returnName = null;
        if (hasRowMapper) {
            simpleJdbcCall.returningResultSet(RESULTSET_NAME, rowMapper);
            returnName = RESULTSET_NAME;
        } else if (type != SqlTypeValue.TYPE_UNKNOWN && !hasOutParameter) {
            SqlOutParameter sqlOutParameter = new SqlOutParameter(OUTPARAMETER_NAME, type);
            parameters.add(0, new AbstractMap.SimpleEntry<>(Direction.OUT, sqlOutParameter));
            returnName = OUTPARAMETER_NAME;
        }

        parameters.forEach(entry -> simpleJdbcCall.addDeclaredParameter(entry.getValue()));


        return new AbstractMap.SimpleEntry<>(returnName, simpleJdbcCall);
    }

    protected enum Direction {
        IN, OUT
    }
}
