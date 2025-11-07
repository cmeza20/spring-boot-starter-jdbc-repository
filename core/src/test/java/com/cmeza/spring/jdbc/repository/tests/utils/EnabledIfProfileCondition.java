package com.cmeza.spring.jdbc.repository.tests.utils;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.platform.commons.util.Preconditions;

public class EnabledIfProfileCondition extends AbstractRepeatableAnnotationCondition<EnabledIfProfile> {
    private static final ConditionEvaluationResult ENABLED = ConditionEvaluationResult.enabled("No @EnabledIfSystemProperty conditions resulting in 'disabled' execution encountered");

    EnabledIfProfileCondition() {
        super(EnabledIfProfile.class);
    }

    @Override
    protected ConditionEvaluationResult evaluate(EnabledIfProfile annotation) {
        String name = annotation.named().trim();
        String inheritedProfile = annotation.inheritedProfile().trim();
        String[] profiles = annotation.profiles();
        Preconditions.notBlank(name, () -> "The 'named' attribute must not be blank in " + annotation);
        Preconditions.notBlank(inheritedProfile, () -> "The 'inheritedProfile' attribute must not be blank in " + annotation);
        Preconditions.notNull(profiles, () -> "The 'profiles' attribute must not be blank in " + annotation);
        String actual = System.getProperty(name);
        if (actual == null) {
            return ConditionEvaluationResult.disabled(String.format("Profile [%s] does not exist", name), "");
        } else {
            if (actual.matches(inheritedProfile)) {
                return ConditionEvaluationResult.enabled(String.format("Profile [%s] with value [%s] matches regular expression [%s]", name, actual, inheritedProfile));
            }
            for (String profile : profiles) {
                if (actual.matches(profile)) {
                    return ConditionEvaluationResult.enabled(String.format("Profile [%s] with value [%s] matches regular expression [%s]", name, actual, profile));
                }
            }
            return ConditionEvaluationResult.disabled(String.format("Profile [%s] with value [%s] does not match regular expression", name, actual));
        }
    }

    @Override
    protected ConditionEvaluationResult getNoDisabledConditionsEncounteredResult() {
        return ENABLED;
    }
}
