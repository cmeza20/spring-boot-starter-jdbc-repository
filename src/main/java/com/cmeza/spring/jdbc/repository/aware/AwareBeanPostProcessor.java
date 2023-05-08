package com.cmeza.spring.jdbc.repository.aware;

import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;

public class AwareBeanPostProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public AwareBeanPostProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof JdbcRepositoryAware) {
            JdbcRepositoryAware jdbcRepositoryAware = (JdbcRepositoryAware)bean;
            jdbcRepositoryAware.setPropertiesResolver(applicationContext.getBean(JdbcPropertyResolver.class));
            jdbcRepositoryAware.setJdbcRepositoryTemplate(applicationContext.getBean(JdbcRepositoryTemplate.class));
            jdbcRepositoryAware.setJdbcRepositoryTemplate(applicationContext.getBeansOfType(JdbcRepositoryTemplate.class));
            return bean;
        }
        return bean;
    }
}
