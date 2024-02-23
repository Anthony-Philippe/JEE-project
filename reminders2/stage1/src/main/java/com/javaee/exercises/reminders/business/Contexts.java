package com.javaee.exercises.reminders.business;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ContextsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Contexts {

    String message() default "Wrong context name (must follow this pattern: [a-z0-9]+)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
