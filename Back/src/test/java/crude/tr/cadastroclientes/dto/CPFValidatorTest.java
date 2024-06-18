package crude.tr.cadastroclientes.dto;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CPFValidatorTest {

    private CPFValidator cpfValidator;

    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks anotados com @Mock
        MockitoAnnotations.openMocks(this);
        // Instancia o validador
        cpfValidator = new CPFValidator();
        // Inicializa o validador com a configuração necessária nesse caso null
        cpfValidator.initialize(null);
    }

    @Test
    public void givenNullCPF_whenIsValid_thenReturnFalse() {
        // Arrange
        String cpf = null;

        // Act
        boolean result = cpfValidator.isValid(null, context);

        // Assert
        assertFalse(result);
    }

    @Test
    public void givenBlankCPF_whenIsValid_thenReturnFalse() {
        // Arrange
        String cpf = " ";

        // Act
        boolean result = cpfValidator.isValid(cpf, context);

        // Assert
        assertFalse(result);
    }

    @Test
    public void givenCPFWithNonNumericCharacters_whenIsValid_thenReturnFalse() {
        // Arrange
        String cpf = "1234567890a";

        // Act
        boolean result = cpfValidator.isValid(cpf, context);

        // Assert
        assertFalse(result);
    }

    @Test
    public void givenCPFWithAllEqualDigits_whenIsValid_thenReturnFalse() {
        // Arrange
        String cpf = "11111111111";

        // Act
        boolean result = cpfValidator.isValid(cpf, context);

        // Assert
        assertFalse(result);
    }

    @Test
    public void givenValidCPF_whenIsValid_thenReturnTrue() {
        // Arrange
        String cpf = "12345678909";

        // Act
        boolean result = cpfValidator.isValid(cpf, context);

        // Assert
        assertTrue(result);
    }

    @Test
    public void givenInvalidCPF_whenIsValid_thenReturnFalse() {
        // Arrange
        String cpf = "12345678900";

        // Act
        boolean result = cpfValidator.isValid(cpf, context);

        // Assert
        assertFalse(result);
    }

    @Test
    public void givenCPFWithCorrectLengthButInvalidCheckDigit_whenIsValid_thenReturnFalse() {
        // Arrange
        String cpf = "12345678901";

        // Act
        boolean result = cpfValidator.isValid(cpf, context);

        // Assert
        assertFalse(result);
    }
}
