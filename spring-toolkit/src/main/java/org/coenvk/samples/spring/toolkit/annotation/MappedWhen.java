package org.coenvk.samples.spring.toolkit.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Declares that the field may be copied by the helpers in {@link ToolkitReflection}.
 * This can be used for example in the update and create requests for models.
 */
@Documented
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface MappedWhen {
    /**
     * Declares the condition when to copy the value
     *
     * <p>Defaults to {@code Object.class}.
     */
    MappingCondition value() default MappingCondition.ALWAYS;

    enum MappingCondition {
        /**
         * Never copy.
         */
        NEVER,
        /**
         * Always copy.
         */
        ALWAYS,

        /**
         * Copy when the value equals null.
         */
        NULL,

        /**
         * Copy when the value is not null.
         */
        NOT_NULL
    }
}