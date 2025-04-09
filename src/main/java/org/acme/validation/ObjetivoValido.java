package org.acme.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ObjetivoValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjetivoValido {
    String message() default "Objetivo inválido. Valores permitidos: Hipertrofia, Resistência, Condicionamento, Perda de Peso";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
