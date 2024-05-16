package crude.tr.cadastroclientes.dto;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern CPF_EQUAL_DIGITS_PATTERN = Pattern.compile("^(\\d)\\1+$");

    @Override
    public void initialize(ValidCPF registrationNumber) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }

        // Remover caracteres não numéricos.
        String cpfClean = cpf.replaceAll("\\D", "");

        // Verificar se o CPF tem 11 dígitos e não são todos iguais.
        if (!CPF_PATTERN.matcher(cpfClean).matches() || CPF_EQUAL_DIGITS_PATTERN.matcher(cpfClean).matches()) {
            return false;
        }

        // Calcular os dígitos verificadores.
        return calculateCheckDigit(cpfClean, 9) && calculateCheckDigit(cpfClean, 10);
    }

    private boolean calculateCheckDigit(String cpf, int length) {
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (length + 1 - i);
        }

        int remainder = 11 - (sum % 11);
        if (remainder == 10 || remainder == 11) {
            remainder = 0;
        }

        return remainder == Character.getNumericValue(cpf.charAt(length));
    }
}
