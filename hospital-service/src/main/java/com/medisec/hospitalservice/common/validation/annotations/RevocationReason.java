package com.medisec.hospitalservice.common.validation.annotations;


import com.medisec.hospitalservice.common.validation.validators.RevocationReasonValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RevocationReasonValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public  @interface RevocationReason {
    String message() default "Revocation reason must be between 0 and 10, excluding 7";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
