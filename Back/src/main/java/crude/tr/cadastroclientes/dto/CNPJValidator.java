package crude.tr.cadastroclientes.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CNPJValidator implements ConstraintValidator<ValidRegistration, String> {

    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern CPF_EQUAL_DIGITS_PATTERN = Pattern.compile("^(\\d)\\1+$");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("^\\d{14}$");
    private static final Pattern CNPJ_EQUAL_DIGITS_PATTERN = Pattern.compile("^(\\d)\\1+$");

    @Override
    public void initialize(ValidRegistration registrationNumber) {
    }

    @Override
    public boolean isValid(String registrationNumber, ConstraintValidatorContext context) {
        if (registrationNumber == null || registrationNumber.isBlank()) {
            return false;
        }

        // Remover caracteres não numéricos.
        String registrationClean = registrationNumber.replaceAll("\\D", "");

        // Verificar se o registrationNumber tem 11 dígitos (CPF) ou 14 dígitos (CNPJ) e não são todos iguais.
        if (registrationClean.length() == 11) {
            return CPF_PATTERN.matcher(registrationClean).matches() && !CPF_EQUAL_DIGITS_PATTERN.matcher(registrationClean).matches() && calculateCheckDigit(registrationClean, 9) && calculateCheckDigit(registrationClean, 10);
        } else if (registrationClean.length() == 14) {
            return CNPJ_PATTERN.matcher(registrationClean).matches() && !CNPJ_EQUAL_DIGITS_PATTERN.matcher(registrationClean).matches() && calculateCheckDigit(registrationClean, 12) && calculateCheckDigit(registrationClean, 13);
        } else {
            return false;
        }
    }

    private boolean calculateCheckDigit(String registration, int length) {
        int[] weight = (length == 9) ? new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2} :
                new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        for (int i = length - 1, digit; i >= 0; i--) {
            digit = Character.getNumericValue(registration.charAt(i));
            sum += digit * weight[weight.length - length + i];
        }

        int remainder = sum % 11 < 2 ? 0 : 11 - sum % 11;
        return remainder == Character.getNumericValue(registration.charAt(length));
    }
}

