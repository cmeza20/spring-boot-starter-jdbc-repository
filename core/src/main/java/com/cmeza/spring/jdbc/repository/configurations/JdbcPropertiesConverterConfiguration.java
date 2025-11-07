package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.jdbc.repository.support.binding.converters.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class JdbcPropertiesConverterConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public SqlTypeConverter sqlTypeConverter() {
        return new SqlTypeConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcCallConverter jdbcCallConverter() {
        return new JdbcCallConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcExecuteConverter jdbcExecuteConverter() {
        return new JdbcExecuteConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcFunctionConverter jdbcFunctionConverter() {
        return new JdbcFunctionConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcInsertConverter jdbcInsertConverter() {
        return new JdbcInsertConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcPaginationConverter jdbcPaginationConverter() {
        return new JdbcPaginationConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcProcedureConverter jdbcProcedureConverter() {
        return new JdbcProcedureConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcQueryConverter jdbcQueryConverter() {
        return new JdbcQueryConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcRawPaginationConverter jdbcRawPaginationConverter() {
        return new JdbcRawPaginationConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcRawQueryConverter jdbcRawQueryConverter() {
        return new JdbcRawQueryConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcRawUpdateConverter jdbcRawUpdateConverter() {
        return new JdbcRawUpdateConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public ParameterConverter parameterConverter() {
        return new ParameterConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcCountQueryConverter jdbcCountQueryConverter() {
        return new JdbcCountQueryConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcMappingConverter jdbcMappingConverter() {
        return new JdbcMappingConverter();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdbcRawCountQueryConverter jdbcRawCountQueryConverter() {
        return new JdbcRawCountQueryConverter();
    }
}
