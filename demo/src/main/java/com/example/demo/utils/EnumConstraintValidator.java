package com.example.demo.utils;

import com.example.demo.annotation.Enum;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumConstraintValidator implements ConstraintValidator<Enum, String> {
    private String[] verification;

    @Override
    public void initialize(Enum anEnum) {
        verification = anEnum.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(verification)) {
            return true;
        }
        if (null == value) {
            return true;
        }
        for (String str : verification) {
            if (str.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
