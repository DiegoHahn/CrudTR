package crude.tr.cadastroclientes.dto;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CNPJValidatorTest {

    private CNPJValidator cnpjValidator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);
    }

    @Test
    public void givenNullRegistrationNumber_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid(null, context);
        assertFalse(result);
    }

    @Test
    public void givenBlankRegistrationNumber_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid(" ", context);
        assertFalse(result);
    }

    @Test
    public void givenRegistrationNumberWithNonNumericCharacters_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("1234567890123a", context);
        assertFalse(result);
    }

    @Test
    public void givenCPFWithAllEqualDigits_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("11111111111", context);
        assertFalse(result);
    }

    @Test
    public void givenCNPJWithAllEqualDigits_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("11111111111111", context);
        assertFalse(result);
    }

    @Test
    public void givenValidCPF_whenIsValid_thenReturnTrue() {
        boolean result = cnpjValidator.isValid("12345678909", context);
        assertTrue(result);
    }

    @Test
    public void givenValidCNPJ_whenIsValid_thenReturnTrue() {
        boolean result = cnpjValidator.isValid("12345678000195", context);
        assertTrue(result);
    }

    @Test
    public void givenInvalidCPF_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("12345678900", context);
        assertFalse(result);
    }

    @Test
    public void givenInvalidCNPJ_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("12345678000190", context);
        assertFalse(result);
    }

    @Test
    public void givenCPFWithCorrectLengthButInvalidCheckDigit_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("12345678901", context);
        assertFalse(result);
    }

    @Test
    public void givenCNPJWithCorrectLengthButInvalidCheckDigit_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("12345678000191", context);
        assertFalse(result);
    }

    @Test
    public void givenInvalidLengthRegistrationNumber_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("123456789", context);
        assertFalse(result);
    }

    @Test
    public void givenCPFWithValidPatternButAllEqualDigits_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("11111111111", context);
        assertFalse(result);
    }

    @Test
    public void givenCNPJWithValidPatternButAllEqualDigits_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("11111111111111", context);
        assertFalse(result);
    }

    @Test
    public void givenCPFWithValidPatternAndUnequalDigitsButInvalidCheckDigits_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("12345678901", context);
        assertFalse(result);
    }

    @Test
    public void givenCNPJWithValidPatternAndUnequalDigitsButInvalidCheckDigits_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("12345678000191", context);
        assertFalse(result);
    }

    @Test
    public void givenCPFWithValidPatternUnequalDigitsAndFirstCheckDigitButInvalidSecondCheckDigit_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("11144477730", context);
        assertFalse(result);
    }

    @Test
    public void givenCNPJWithValidPatternUnequalDigitsAndFirstCheckDigitButInvalidSecondCheckDigit_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("11444777000150", context);
        assertFalse(result);
    }

    @Test
    public void givenCPFWithValidPatternButInvalidFirstCheckDigit_whenIsValid_thenReturnFalse() {
        // This CPF has a valid pattern but an invalid first check digit
        boolean result = cnpjValidator.isValid("11144477720", context);
        assertFalse(result);
    }
    @Test
    public void givenCNPJWithValidPatternButInvalidFirstCheckDigit_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("11444777000140", context);
        assertFalse(result);
    }
    @Test
    public void givenRegistrationNumberWithInvalidLength_whenIsValid_thenReturnFalse() {
        boolean result = cnpjValidator.isValid("123456789012", context);
        assertFalse(result);
    }

    @Test
    public void givenCPFWithValidPatternUnequalDigitsButFailsFirstCheckDigit_whenIsValid_thenReturnFalse() {
        // This CPF has a valid pattern, unequal digits, but fails on the first check digit
        boolean result = cnpjValidator.isValid("12345678910", context);
        assertFalse(result);
    }

    @Test
    public void givenCNPJWithValidPatternUnequalDigitsButFailsFirstCheckDigit_whenIsValid_thenReturnFalse() {
        // This CNPJ has a valid pattern, unequal digits, but fails on the first check digit
        boolean result = cnpjValidator.isValid("12345678000180", context);
        assertFalse(result);
    }
}