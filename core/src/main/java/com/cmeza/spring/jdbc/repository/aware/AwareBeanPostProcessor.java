package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

public class AwareBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public AwareBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean instanceof JdbcRepositoryAware jdbcRepositoryAware) {

            String jdbcRepositoryBeanName = jdbcRepositoryAware.getQualifier();
            Assert.hasLength(jdbcRepositoryBeanName, "JdbcRepositoryAware qualifier is required!");

            Map<String, JdbcRepositoryTemplate> jdbcRepositoryTemplates = applicationContext.getBeansOfType(JdbcRepositoryTemplate.class);

            JdbcRepositoryTemplate jdbcRepositoryTemplate = jdbcRepositoryTemplates.get(jdbcRepositoryBeanName);
            if (jdbcRepositoryAware.findQualified() && Objects.isNull(jdbcRepositoryTemplate)) {
                throw new BeanInstantiationException(bean.getClass(), "No bean qualifier for: " + jdbcRepositoryBeanName);
            }

            jdbcRepositoryAware.setPropertiesResolver(applicationContext.getBean(JdbcPropertyResolver.class));
            jdbcRepositoryAware.setJdbcRepositoryTemplate(jdbcRepositoryTemplates.get(jdbcRepositoryBeanName));
            jdbcRepositoryAware.setNamingStrategy(applicationContext.getBean(NamingStrategy.class));
            return bean;
        }
        return bean;
    }
}
