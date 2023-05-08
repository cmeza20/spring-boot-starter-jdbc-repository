package com.cmeza.spring.jdbc.repository.interceptors;

import com.cmeza.spring.ioc.handler.handlers.IocMethodInterceptor;
import com.cmeza.spring.ioc.handler.handlers.IocTarget;
import com.cmeza.spring.jdbc.repository.annotations.JdbcRepository;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

public class JdbcRepositoryTemplateMethodInterceptor implements IocMethodInterceptor<JdbcRepositoryTemplate>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public Optional<Object> invoke(Object o, IocTarget<?> target, Method method, Object[] objects) {
        Optional<Object> beanFromInterceptor = JdbcUtils.getBeanFromInterceptor(applicationContext, method);

        if (beanFromInterceptor.isPresent()) {
            return beanFromInterceptor;
        }

        JdbcRepository jdbcRepository = target.getType().getAnnotation(JdbcRepository.class);
        if (Objects.nonNull(jdbcRepository)) {
            return Optional.of(applicationContext.getBean(jdbcRepository.repositoryTemplateBeanName()));
        }

        return Optional.of(applicationContext.getBean(JdbcRepositoryTemplate.class));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
