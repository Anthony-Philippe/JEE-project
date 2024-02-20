package com.javaee.exercises.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomerNumberValidator implements
        ConstraintValidator<CustomerNumber, String> {
    @Override
    public void initialize(CustomerNumber number) {
    }

    @Override
    public boolean isValid(String customerNumber, ConstraintValidatorContext
            ctx) {
        if (customerNumber == null) {
            return false;
        }
        return customerNumber.matches("^[a-zA-Z]{2}-\\d+$");
    }
}