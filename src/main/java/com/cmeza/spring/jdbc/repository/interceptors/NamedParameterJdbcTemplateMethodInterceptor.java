package com.cmeza.spring.jdbc.repository.interceptors;

import com.cmeza.spring.ioc.handler.handlers.IocMethodInterceptor;
import com.cmeza.spring.ioc.handler.handlers.IocTarget;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Method;
import java.util.Optional;

public class NamedParameterJdbcTemplateMethodInterceptor implements IocMethodInterceptor<NamedParameterJdbcTemplate>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Optional<Object> invoke(Object o, IocTarget<?> target, Method method, Object[] objects) {
        Optional<Object> beanFromInterceptor = JdbcUtils.getBeanFromInterceptor(applicationContext, method);

        if (beanFromInterceptor.isPresent()) {
            return beanFromInterceptor;
        }

        return Optional.of(applicationContext.getBean(NamedParameterJdbcTemplate.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
