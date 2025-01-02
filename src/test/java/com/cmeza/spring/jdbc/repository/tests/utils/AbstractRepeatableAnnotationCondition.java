package com.cmeza.spring.jdbc.repository.tests.utils;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.platform.commons.util.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

public abstract class AbstractRepeatableAnnotationCondition <A extends Annotation> implements ExecutionCondition {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Class<A> annotationType;

    AbstractRepeatableAnnotationCondition(Class<A> annotationType) {
        this.annotationType = annotationType;
    }

    public final ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Optional<AnnotatedElement> optionalElement = context.getElement();
        if (optionalElement.isPresent()) {
            AnnotatedElement annotatedElement = optionalElement.get();
            return AnnotationUtils.findRepeatableAnnotations(annotatedElement, this.annotationType).stream().map(annotation -> {
                ConditionEvaluationResult result = this.evaluate(annotation);
                this.logResult(annotation, annotatedElement, result);
                return result;
            }).filter(ConditionEvaluationResult::isDisabled).findFirst().orElse(this.getNoDisabledConditionsEncounteredResult());
        } else {
            return this.getNoDisabledConditionsEncounteredResult();
        }
    }

    protected abstract ConditionEvaluationResult evaluate(A var1);

    protected abstract ConditionEvaluationResult getNoDisabledConditionsEncounteredResult();

    private void logResult(A annotation, AnnotatedElement annotatedElement, ConditionEvaluationResult result) {
        this.logger.trace(() -> String.format("Evaluation of %s on [%s] resulted in: %s", annotation, annotatedElement, result));
    }
}
