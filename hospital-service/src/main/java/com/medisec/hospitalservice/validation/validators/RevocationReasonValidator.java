package com.medisec.hospitalservice.validation.validators;


import com.medisec.hospitalservice.validation.annotations.RevocationReason;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RevocationReasonValidator implements
        ConstraintValidator<RevocationReason, Integer> {

    @Override
    public boolean isValid(Integer reasonField, ConstraintValidatorContext constraintValidatorContext) {
        return reasonField >= 0 && reasonField <= 10 && reasonField != 7;
    }
}