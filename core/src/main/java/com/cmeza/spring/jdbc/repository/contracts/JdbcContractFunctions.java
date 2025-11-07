package com.cmeza.spring.jdbc.repository.contracts;

import com.cmeza.spring.ioc.handler.contracts.consumers.ClassConsumer;
import com.cmeza.spring.ioc.handler.contracts.consumers.MethodConsumer;
import com.cmeza.spring.ioc.handler.contracts.consumers.ParameterConsumer;
import com.cmeza.spring.ioc.handler.metadata.MethodMetadata;
import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleTypeMetadata;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import com.cmeza.spring.jdbc.repository.projections.JdbcProjectionRowMapper;
import com.cmeza.spring.jdbc.repository.repositories.configuration.SimpleJdbcConfiguration;
import com.cmeza.spring.jdbc.repository.support.definitions.*;
import com.cmeza.spring.jdbc.repository.support.exceptions.InvalidParameterSqlTypeException;
import com.cmeza.spring.jdbc.repository.repositories.executors.JdbcExecutor;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.template.pagination.JdbcPageRequest;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class JdbcContractFunctions implements ApplicationContextAware {

    public static final String METHOD_BUILDER = "METHOD_BUILDER";
    public static final String METHOD_EXECUTOR = "METHOD_EXECUTOR";
    public static final String METHOD_NEED_ROWMAPPER = "METHOD_NEED_ROWMAPPER";
    public static final String METHOD_ROWMAPPER = "METHOD_ROWMAPPER";
    public static final String METHOD_PARAMETERS = "METHOD_PARAMETERS";
    public static final String METHOD_LOGGABLE = "METHOD_LOGGABLE";
    public static final String METHOD_PARAM_MAPPINGS = "METHOD_PARAM_MAPPINGS";
    public static final String METHOD_JOIN_TABLES = "METHOD_JOIN_TABLES";
    public static final String METHOD_FROM_TABLE = "METHOD_JOIN_TABLE";
    public static final String METHOD_COUNT_QUERY = "METHOD_COUNT_QUERY";
    public static final String PARAMETER_NAME = "PARAMETER_NAME";
    public static final String PARAMETER_TYPE = "PARAMETER_TYPE";
    protected final MethodConsumer afterAnnotationMethodProcessor = (classMetadata, methodMetadata) -> {
        if (!methodMetadata.isIntercepted()) {
            Assert.isTrue(methodMetadata.hasAttribute(METHOD_EXECUTOR), methodMetadata.getConfigKey() + " - An annotation is required");
        }
    };
    protected final ParameterConsumer onEndParameterConsumer = (classMetadata, methodMetadata, parameterMetadata, index) -> {
        if (!methodMetadata.isIntercepted()) {
            ParameterDefinition[] parameters = methodMetadata.getAttribute(METHOD_PARAMETERS, ParameterDefinition[].class);

            String name = parameterMetadata.getAttribute(PARAMETER_NAME, String.class);
            int type = parameterMetadata.getAttribute(PARAMETER_TYPE, Integer.class, 0);
            TypeMetadata typeMetadata = parameterMetadata.getTypeMetadata();
            boolean isRawTypeCustomArgument = false;
            if (typeMetadata.isArray() && Objects.nonNull(typeMetadata.getArgumentClass())) {
                SimpleTypeMetadata simpleTypeMetadata = new SimpleTypeMetadata(typeMetadata.getArgumentClass());
                isRawTypeCustomArgument = simpleTypeMetadata.isCustomArgumentObject();
            }

            ParameterDefinition parameterDefinition = new ParameterDefinition();
            parameterDefinition.setParameterName(Objects.nonNull(name) ? name : parameterMetadata.getParameter().getName());
            parameterDefinition.setParameterType(type);
            parameterDefinition.setPosition(index);
            parameterDefinition.setTypeMetadata(typeMetadata);
            parameterDefinition.setArray(typeMetadata.isArray());
            parameterDefinition.setCollection(typeMetadata.isList() || typeMetadata.isSet() || typeMetadata.isStream() || typeMetadata.isArray());
            parameterDefinition.setBean(typeMetadata.isCustomArgumentObject() && !typeMetadata.isList() && !typeMetadata.isMap() && !typeMetadata.isSet() && !typeMetadata.isArray() && !typeMetadata.isStream());
            parameterDefinition.setBatch(isRawTypeCustomArgument || (typeMetadata.isCustomArgumentObject() && (typeMetadata.isList() || typeMetadata.isMap() || typeMetadata.isSet() || typeMetadata.isStream())));
            parameterDefinition.setPageRequest(typeMetadata.getRawClass().isAssignableFrom(JdbcPageRequest.class));
            parameters[index] = parameterDefinition;

            if (type != 0) {
                if (!JdbcUtils.hasType(type)) {
                    throw new InvalidParameterSqlTypeException(methodMetadata.getConfigKey() + " - Invalid property type of parameter [" + parameterDefinition.getParameterName() + "], use java.sql.Types constants");
                }
                parameterDefinition.setParameterTypeName(JdbcUtils.getType(type));
            }

            methodMetadata.addAttribute(METHOD_PARAMETERS, parameters);
        }
    };
    private ApplicationContext applicationContext;
    private JdbcRepositoryTemplate jdbcRepositoryTemplate;
    protected final MethodConsumer onStartMethodConsumer = (classMetadata, methodMetadata) -> {
        if (!methodMetadata.isIntercepted()) {
            SimpleJdbcConfiguration.Builder builder = SimpleJdbcConfiguration.builder().jdbcTemplate(jdbcRepositoryTemplate).typeMetadata(methodMetadata.getTypeMetadata()).configKey(methodMetadata.getConfigKey());

            methodMetadata.addAttribute(METHOD_BUILDER, builder);
            methodMetadata.addAttribute(METHOD_PARAMETERS, new ParameterDefinition[methodMetadata.getMethod().getParameterCount()]);
        }
    };
    private JdbcRepositoryProperties jdbcRepositoryProperties;
    protected final ClassConsumer afterAnnotationClassProcessor = (classMetadata -> {
        JdbcRepository jdbcRepository = classMetadata.getProcessorResult(JdbcRepository.class);
        jdbcRepositoryTemplate = applicationContext.getBean(jdbcRepository.repositoryTemplateBeanName(), JdbcRepositoryTemplate.class);
        jdbcRepositoryProperties = applicationContext.getBean(JdbcRepositoryProperties.class);
    });
    protected final MethodConsumer onEndMethodConsumer = (classMetadata, methodMetadata) -> {
        if (!methodMetadata.isIntercepted()) {
            boolean needRowMapper = methodMetadata.getAttribute(METHOD_NEED_ROWMAPPER, Boolean.class);
            SimpleJdbcConfiguration.Builder builder = methodMetadata.getAttribute(METHOD_BUILDER, SimpleJdbcConfiguration.Builder.class);
            builder.loggable(jdbcRepositoryProperties.isLoggable() || methodMetadata.getAttribute(METHOD_LOGGABLE, Boolean.class, false));
            builder.targetClass(classMetadata.getTargetClass());
            builder.needRowMapper(needRowMapper);

            JdbcExecutor executor = methodMetadata.getAttribute(METHOD_EXECUTOR, JdbcExecutor.class);
            TypeMetadata typeMetadata = methodMetadata.getTypeMetadata();

            //RowMapper
            if (needRowMapper) {
                Class<? extends RowMapper<?>> rowMapperClass = methodMetadata.getAttribute(METHOD_ROWMAPPER, Class.class);
                boolean isSingleColumnMapperType = JdbcUtils.isSingleColumnMapperType(typeMetadata.getArgumentClass());
                if (Objects.nonNull(rowMapperClass)) {
                    if (typeMetadata.getArgumentClass().isInterface()) {
                        JdbcUtils.projectionClassValidate(rowMapperClass);
                    }
                    this.validateRowMapper(methodMetadata, rowMapperClass);
                    builder.rowMapper(jdbcRepositoryTemplate.registerJdbcRowMapperBean(rowMapperClass));
                } else if (typeMetadata.getRawClass().isAssignableFrom(Map.class) || typeMetadata.getArgumentClass().isAssignableFrom(Map.class)) {
                    builder.rowMapper(new ColumnMapRowMapper());
                } else if (isSingleColumnMapperType) {
                    builder.rowMapper(new SingleColumnRowMapper<>(typeMetadata.getArgumentClass()));
                } else if (typeMetadata.getArgumentClass().isInterface()) {
                    builder.rowMapper(JdbcProjectionRowMapper.newInstance(typeMetadata.getArgumentClass()));
                } else {
                    builder.rowMapper(JdbcRowMapper.newInstance(typeMetadata.getArgumentClass()));
                }
            }

            //Parameters
            builder.parameters(methodMetadata.getAttribute(METHOD_PARAMETERS, ParameterDefinition[].class, new ParameterDefinition[0]));

            //Mappings
            builder.mappings(methodMetadata.getAttribute(METHOD_PARAM_MAPPINGS, MappingDefinition[].class, new MappingDefinition[0]));

            //Join Tables
            builder.joinTables(methodMetadata.getAttribute(METHOD_JOIN_TABLES, JoinTableDefinition[].class, new JoinTableDefinition[0]));

            //From Table
            builder.fromTable(methodMetadata.getAttribute(METHOD_FROM_TABLE, TableDefinition.class));

            //Count Query
            builder.countQuery(methodMetadata.getAttribute(METHOD_COUNT_QUERY, QueryDefinition.class));

            executor.attachConfiguration(builder.build());
            executor.print();
            methodMetadata.addAttribute(METHOD_EXECUTOR, executor);
        }
    };

    private void validateRowMapper(MethodMetadata methodMetadata, Class<?> rowMapperClass) {
        Class<?> genericClass = JdbcUtils.getGenericClass(rowMapperClass);
        Assert.isTrue(!(genericClass.isPrimitive() || genericClass.getName().startsWith("java.lang") || genericClass.getName().startsWith("java.util")), methodMetadata.getConfigKey() + " - RowMapper type is not supported: " + genericClass.getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
