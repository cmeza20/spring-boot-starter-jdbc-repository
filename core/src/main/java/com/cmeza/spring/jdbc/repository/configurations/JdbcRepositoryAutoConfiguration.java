package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.ioc.handler.builders.Ioc;
import com.cmeza.spring.ioc.handler.configuration.IocAutoConfiguration;
import com.cmeza.spring.ioc.handler.contracts.IocContract;
import com.cmeza.spring.ioc.handler.processors.AnnotatedClassProcessor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedMethodProcessor;
import com.cmeza.spring.ioc.handler.processors.AnnotatedParameterProcessor;
import com.cmeza.spring.jdbc.repository.aware.AwareBeanPostProcessor;
import com.cmeza.spring.jdbc.repository.contracts.JdbcContract;
import com.cmeza.spring.jdbc.repository.dsl.properties.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.processors.classes.RepositoryAnnotatedClassProcessor;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolverImpl;
import com.cmeza.spring.jdbc.repository.support.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

@ConditionalOnBean(DataSource.class)
@AutoConfigureOrder(Ioc.IOC_ORDER + 1)
@AutoConfiguration(after = IocAutoConfiguration.class)
@EnableConfigurationProperties(JdbcRepositoryProperties.class)
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@Import({JdbcMethodInterceptorsConfiguration.class, JdbcMethodProcessorConfiguration.class, JdbcMethodProcessorDslConfiguration.class, JdbcPropertiesConverterConfiguration.class})
public class JdbcRepositoryAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(NamedParameterJdbcTemplate.class)
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean(JdbcRepositoryTemplate.class)
    public JdbcRepositoryTemplate jdbcRepositoryTemplate(DataSource dataSource) {
        return new JdbcRepositoryTemplate(dataSource);
    }

    @Bean
    public IocContract<JdbcRepository> jdbcContract(List<AnnotatedClassProcessor<?>> classProcessors, List<AnnotatedMethodProcessor<?>> methodProcessors, List<AnnotatedParameterProcessor<?>> parameterProcessors, JdbcRepositoryProperties jdbcRepositoryProperties) {
        return new JdbcContract(classProcessors, methodProcessors, parameterProcessors, jdbcRepositoryProperties);
    }

    @Bean
    public AnnotatedClassProcessor<JdbcRepository> repositoryAnnotatedClassProcessor() {
        return new RepositoryAnnotatedClassProcessor();
    }

    @Bean
    public JdbcPropertyResolver jdbcPropertyResolver(Environment environment) {
        return new JdbcPropertyResolverImpl(environment);
    }

    @Bean
    public BeanPostProcessor awareBeanPostProcessor(ApplicationContext applicationContext) {
        return new AwareBeanPostProcessor(applicationContext);
    }

    @Bean
    @ConditionalOnMissingBean
    public NamingStrategy namingStrategy(JdbcRepositoryProperties jdbcRepositoryProperties) {
        return BeanUtils.instantiateClass(jdbcRepositoryProperties.getNamingStrategy());
    }
}
