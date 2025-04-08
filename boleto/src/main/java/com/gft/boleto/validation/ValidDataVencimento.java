package com.gft.boleto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DataVencimentoValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDataVencimento {
    String message() default "Data de Vencimento deve ser igual ou posterior a data atual.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}