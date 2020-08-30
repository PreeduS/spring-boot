package com.example.demo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BetweenValidator implements ConstraintValidator<Between, Integer> {            // ConstraintValidator<Annotation, T> T - what type to validate
    private int min;
    private int max;
    @Override
	public void initialize(Between between) {
        min = between.min();
        max = between.max();
	}


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return value >= min && value <= max;
    } 

}
