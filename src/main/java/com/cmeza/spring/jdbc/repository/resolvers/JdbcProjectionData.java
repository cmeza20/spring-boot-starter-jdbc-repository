package com.cmeza.spring.jdbc.repository.resolvers;

import com.cmeza.spring.ioc.handler.utils.IocUtil;

@SuppressWarnings("unchecked")
public interface JdbcProjectionData<T, C> {
    default Class<T> getProjectionClass() {
        return (Class<T>) IocUtil.getGenericInterface(this);
    }

    default Class<C> getProjectionImplClass() {
        return (Class<C>) IocUtil.getGenericInterface(this, 1);
    }
}
