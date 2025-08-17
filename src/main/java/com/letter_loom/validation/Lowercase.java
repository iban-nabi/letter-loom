package com.letter_loom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation validating if string is in lowercase
 */
@Target(ElementType.FIELD) // target fields for DTO request parameters in controller class functions
@Retention(RetentionPolicy.RUNTIME) // Apply annotation in runtime
@Constraint(validatedBy = LowercaseValidator.class) // include the validator class
public @interface Lowercase {
    String message() default "Must be lowercase";

    // Template code when creating custom validation annotations
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
