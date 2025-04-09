package org.acme.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class ObjetivoValidator implements ConstraintValidator<ObjetivoValido, String> {

    private static final Set<String> OBJETIVOS_VALIDOS = Set.of(
            "Hipertrofia", "Resistência", "Condicionamento", "Perda de Peso"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true; // Use @NotBlank separadamente se quiser
        return OBJETIVOS_VALIDOS.contains(value);
    }
}
