package br.com.fiap.pessoa.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {
    String message() default "Data de Nascimento deve ser maior ou igual a 18 anos";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}