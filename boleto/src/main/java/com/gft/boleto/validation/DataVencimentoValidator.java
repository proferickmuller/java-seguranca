package com.gft.boleto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class DataVencimentoValidator implements ConstraintValidator<ValidDataVencimento, LocalDateTime> {

    @Override
    public void initialize(ValidDataVencimento constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDateTime vencimentoData, ConstraintValidatorContext context) {
        if (vencimentoData == null) {
            return false;
        }
        return !vencimentoData.isBefore(LocalDateTime.now());
    }

}