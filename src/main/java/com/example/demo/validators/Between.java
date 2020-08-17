package com.example.demo.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented                                                             // visible in javadoc
@Constraint(validatedBy = BetweenValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Between {
    String message() default "Between validation message {min} - {max}";
    int min();
    int max();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


// @Between(min = 10, max = 100)