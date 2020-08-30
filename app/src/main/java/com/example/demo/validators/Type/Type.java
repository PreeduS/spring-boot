package com.example.demo.validators.Type;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented                                                             // visible in javadoc
@Constraint(validatedBy = TypeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Type {
    String message() default "Field Required";

    boolean flag();
    String[] requiredFields();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}