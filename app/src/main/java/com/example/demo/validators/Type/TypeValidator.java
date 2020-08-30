package com.example.demo.validators.Type;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TypeValidator implements ConstraintValidator<Type, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
  
        return false;
    }
    
}