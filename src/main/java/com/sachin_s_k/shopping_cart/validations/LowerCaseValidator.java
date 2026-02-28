package com.sachin_s_k.shopping_cart.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LowerCaseValidator implements ConstraintValidator<LowerCase, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

    if(value==null){
        return  true;
    }
    return value.equals(value.toLowerCase());
    }
}
