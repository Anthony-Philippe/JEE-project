package com.javaee.exercises.reminders.business;

import java.util.List;
import java.util.Objects;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContextsValidator implements ConstraintValidator<Contexts, List<String>> {
    
    @Override
    public void initialize(Contexts constraintAnnotation) {
        // Rien à faire
    }
    
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if (value != null) {
            return value.stream().allMatch(Objects::nonNull) && value.stream().allMatch(c -> c.matches("[a-z0-9]+"));
        }
        return true;
    }
}
