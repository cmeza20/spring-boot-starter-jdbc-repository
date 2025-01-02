package com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public class InParameterSourceProvider {

    protected String[] inParameterNames;

    public boolean isSetParameterNames() {
        return Objects.nonNull(inParameterNames) && inParameterNames.length > 0;
    }

    public boolean isSetAndNotContains(String parameterName) {
        return isSetParameterNames() && !Arrays.asList(inParameterNames).contains(parameterName);
    }

    public InParameterSourceProvider setInParameterNames(String... inParameterNames) {
        this.inParameterNames = inParameterNames;
        return this;
    }
}
