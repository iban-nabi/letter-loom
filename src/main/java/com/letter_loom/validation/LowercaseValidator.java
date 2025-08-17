package com.letter_loom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//<target validation annotation, data type>
public class LowercaseValidator implements ConstraintValidator<Lowercase, String>{

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || s.isEmpty()) return true;
        return s.equals(s.toLowerCase());
    }
}
