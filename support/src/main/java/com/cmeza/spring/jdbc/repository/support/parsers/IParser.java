package com.cmeza.spring.jdbc.repository.support.parsers;

import com.cmeza.spring.jdbc.repository.support.transform.MapTransform;

import java.lang.annotation.Annotation;

public interface IParser<A extends Annotation, D extends MapTransform> {
    void parse(A annotation, D dslProperty);
}
