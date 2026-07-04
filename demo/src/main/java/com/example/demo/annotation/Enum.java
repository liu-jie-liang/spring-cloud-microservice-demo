package com.example.demo.annotation;

import com.example.demo.utils.EnumConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumConstraintValidator.class)
public @interface Enum {
    String[] value();

    String message() default "{com.example.demo.annotation.Enum.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
