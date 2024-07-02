package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.Exceptions.AccountantNotFoundException;
import crude.tr.cadastroclientes.Exceptions.DeleteAccountantException;
import crude.tr.cadastroclientes.Exceptions.DuplicateAccountantException;
import crude.tr.cadastroclientes.Exceptions.ForeignKeyViolationException;
import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

// Anotação para indicar que a classe de teste deve ser estendida com o MockitoExtension
@ExtendWith(MockitoExtension.class)
public class AccountantServiceTest {

    @Mock
    private AccountantRepository accountantRepository;

    // Instancia a classe a ser testada e injeta os mocks criados (quando o repository é chamado, o mock é retornado)
    @InjectMocks
    private AccountantService accountantService;

    private Accountant accountantTeste;
    private List<Accountant> accountantList;

    @BeforeEach
    public void setUp() {
        accountantTeste = new Accountant(1L, "12345678901", "1123", "Contador1", true);
        Accountant accountant2 = new Accountant(2L, "98765432109", "2456", "Contador2", true);
        accountantList = Arrays.asList(accountantTeste, accountant2);
    }

    @DisplayName("Given AccountantDTO when convertToAccountant then return Accountant")
    @Test
    void testGivenAccountantDTO_whenConvertToAccountant_thenReturnAccountant() {
        // Arrange
        AccountantDTO accountantDTO = new AccountantDTO(1L,"12345678901", "123", "Teste", true);

        // Act
        Accountant accountant = accountantService.convertToAccountant(accountantDTO);

        // Assert
        assertEquals(accountantDTO.getId(), accountant.getId());
        assertEquals(accountantDTO.getRegistrationNumber(), accountant.getRegistrationNumber());
        assertEquals(accountantDTO.getAccountantCode(), accountant.getAccountantCode());
        assertEquals(accountantDTO.getName(), accountant.getName());
        assertEquals(accountantDTO.getIsActive(), accountant.getIsActive());
    }

    @DisplayName("Given name and pageable when findAccountantsOrderedByName then return Accountant Page")
    @Test
    void testGivenNameAndPageable_whenfindAccountantsOrderedByName_thenReturnAccountantPage() {
        // Arrange
        String name = "Teste";
        Pageable pageable = PageRequest.of(0, 10);
        //Criando uma pagina para simular o retorno
        Page<Accountant> accountantPage = new PageImpl<>(accountantList, pageable, accountantList.size());
        when(accountantRepository.findAccountantsByName(name, pageable)).thenReturn(accountantPage);

        // Act
        Page<Accountant> result = accountantService.findAccountantsOrderedByName(name, pageable);

        // Assert
        assertEquals(accountantPage, result);
        assertEquals(2, result.getTotalElements());
        assertEquals(accountantList, result.getContent());
        verify(accountantRepository, times(1)).findAccountantsByName(name, pageable);
    }

    @DisplayName("Given id when findAccountantByID then return Accountant")
    @Test
    void testGivenId_whenFindAccountantByID_thenReturnAccountant() {
        // Arrange
        Long id = 1L;
        when(accountantRepository.findById(id)).thenReturn(Optional.of(accountantTeste));

        // Act
        Optional<Accountant> result = accountantService.findAccountantByID(id);

        // Assert
        assertEquals(Optional.of(accountantTeste), result);
        verify(accountantRepository, times(1)).findById(id);
    }

    @DisplayName("Given Accountant when addAccountant then return Accountant")
    @Test
    void testGivenAccountant_whenAddAccountant_thenReturnAccountant() {
        // Arrange
        when(accountantRepository.findByRegistrationNumber(accountantTeste.getRegistrationNumber())).thenReturn(Optional.empty());
        when(accountantRepository.save(accountantTeste)).thenReturn(accountantTeste);

        // Act
        var result = accountantService.addAccountant(accountantTeste);

        // Assert
        assertEquals(accountantTeste, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(accountantRepository, times(1)).findByRegistrationNumber(accountantTeste.getRegistrationNumber());
        verify(accountantRepository, times(1)).save(accountantTeste);
    }

    @DisplayName("Given existing Accountant when addAccountant then return CONFLICT")
    @Test
    void testGivenExistingAccountant_whenAddAccountant_thenReturnConflict() {
        // Arrange
        when(accountantRepository.findByRegistrationNumber(accountantTeste.getRegistrationNumber())).thenReturn(Optional.of(accountantTeste));

        // Act
        DuplicateAccountantException exception = assertThrows(DuplicateAccountantException.class, () -> {
            accountantService.addAccountant(accountantTeste);
        });
        // Assert
        assertEquals("Registro duplicado", exception.getMessage());
        verify(accountantRepository, times(1)).findByRegistrationNumber(accountantTeste.getRegistrationNumber());
        verify(accountantRepository, times(0)).save(accountantTeste);
    }

    @DisplayName("Given id and AccountantDTO when updateAccountant then return Accountant")
    @Test
    void testGivenIdAndAccountantDTO_whenUpdateAccountant_thenReturnAccountant() {
        // Arrange
        Long accountantId = 1L;
        AccountantDTO accountantDTO = new AccountantDTO(accountantId, "12345678901", "1123", "ContadorAtualizado", true);

        // Mockando o comportamento do repositório
        when(accountantRepository.findById(accountantId)).thenReturn(Optional.of(accountantTeste));
        when(accountantRepository.save(any(Accountant.class))).thenReturn(accountantTeste);

        // Act
        ResponseEntity<Accountant> result = accountantService.updateAccountant(accountantId, accountantDTO);

        // Assert
        assertEquals(accountantTeste, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(accountantRepository, times(1)).findById(accountantId);
        verify(accountantRepository, times(1)).save(accountantTeste);
    }

    @DisplayName("Given id that does not exist when updateAccountant then return Not_Found")
    @Test
    void testGivenIdThatDoesNotExist_whenUpdateAccountant_thenReturnNotFound() {
        // Arrange
        Long accountantId = 1L;
        AccountantDTO accountantDTO = new AccountantDTO(accountantId, "12345678901", "1123", "ContadorAtualizado", true);
        when(accountantRepository.findById(accountantId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Accountant> result = accountantService.updateAccountant(accountantId, accountantDTO);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(accountantRepository, times(1)).findById(accountantId);
        verify(accountantRepository, times(0)).save(any(Accountant.class));
    }

    @DisplayName("Given id that exists in database when deleteAccountant then return HttpStatus NO_CONTENT")
    @Test
    void testGivenIdThatExists_whenDeleteAccountant_thenReturnNoContent() {
        // Arrange
        Long accountantId = 1L;
        when(accountantRepository.findById(accountantId)).thenReturn(Optional.of(accountantTeste));

        // Act
        ResponseEntity<Void> result = accountantService.deleteAccountant(accountantId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(accountantRepository, times(1)).findById(accountantId);
        verify(accountantRepository, times(1)).deleteById(accountantId);
    }

    @DisplayName("Given id that doesn't exist in database when deleteAccountant then throw AccountantNotFoundException")
    @Test
    void testGivenNonExistingId_whenDeleteAccountant_thenThrowAccountantNotFoundException() {
        // Arrange
        Long accountantId = 1L;
        when(accountantRepository.findById(accountantId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountantNotFoundException.class, () -> accountantService.deleteAccountant(accountantId));
        verify(accountantRepository, times(1)).findById(accountantId);
        verify(accountantRepository, never()).deleteById(accountantId);
    }

    @DisplayName("Given id when deleteAccountant throws DataIntegrityViolationException with FK constraint then throw ForeignKeyViolationException")
    @Test
    void testGivenIdThatIsReferencedInAnotherTable_whenDeleteAccountant_thenThrowForeignKeyViolationException() {
        // Arrange
        Long accountantId = 1L;
        Accountant accountant = new Accountant();
        when(accountantRepository.findById(accountantId)).thenReturn(Optional.of(accountant));
        DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException("Constraint violation",
                new SQLException("Violation of foreign key constraint", "23503"));
        doThrow(dataIntegrityViolationException).when(accountantRepository).deleteById(accountantId);

        // Act & Assert
        ForeignKeyViolationException exception = assertThrows(ForeignKeyViolationException.class,
                () -> accountantService.deleteAccountant(accountantId));
        assertEquals("Você não pode excluir um contador que está associado a um cliente", exception.getMessage());
        verify(accountantRepository, times(1)).findById(accountantId);
        verify(accountantRepository, times(1)).deleteById(accountantId);
    }

    @DisplayName("Given id when deleteAccountant throws DataIntegrityViolationException without FK constraint then throw DeleteAccountantException")
    @Test
    void testGivenId_whenDeleteAccountantThrowsDataIntegrityViolationException_thenThrowDeleteAccountantException() {
        // Arrange
        Long accountantId = 1L;
        Accountant accountant = new Accountant();
        when(accountantRepository.findById(accountantId)).thenReturn(Optional.of(accountant));
        DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException("Other integrity violation");
        doThrow(dataIntegrityViolationException).when(accountantRepository).deleteById(accountantId);

        // Act & Assert
        DeleteAccountantException exception = assertThrows(DeleteAccountantException.class,
                () -> accountantService.deleteAccountant(accountantId));
        assertEquals("Não foi possivel excluir esse contador", exception.getMessage());
        verify(accountantRepository, times(1)).findById(accountantId);
        verify(accountantRepository, times(1)).deleteById(accountantId);
    }
}
