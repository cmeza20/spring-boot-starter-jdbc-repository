package com.cmeza.spring.jdbc.repository.interceptors;

import com.cmeza.spring.ioc.handler.handlers.IocMethodInterceptor;
import com.cmeza.spring.ioc.handler.handlers.IocTarget;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import com.cmeza.spring.jdbc.repository.resolvers.JdbcPropertyResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Optional;

public class JdbcPropertyResolverInterceptor implements IocMethodInterceptor<JdbcPropertyResolver>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Optional<Object> invoke(Object o, IocTarget<?> iocTarget, Method method, Object[] objects) {
        Optional<Object> beanFromInterceptor = JdbcUtils.getBeanFromInterceptor(applicationContext, method);

        if (beanFromInterceptor.isPresent()) {
            return beanFromInterceptor;
        }

        return Optional.of(applicationContext.getBean(JdbcPropertyResolver.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
