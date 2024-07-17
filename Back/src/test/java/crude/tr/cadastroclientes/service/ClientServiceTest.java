package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.Exceptions.AccountantNotFoundException;
import crude.tr.cadastroclientes.Exceptions.ClientNotFoundException;
import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.model.CompanyStatus;
import crude.tr.cadastroclientes.model.RegistrationType;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import crude.tr.cadastroclientes.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AccountantRepository accountantRepository;

    @InjectMocks
    private ClientService clientService;

    private Client clientTest;
    private Client client2;
    private ClientDTO clientDTO;
    private Accountant accountantTest;
    private List<Client> clientList;

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();
        accountantTest = new Accountant(1L, "12345678901", "1123", "Contador1", true);
        clientTest = new Client(1L, RegistrationType.CPF, "29563355024", "123", "Test Client", "Test Fantasy Name", now, CompanyStatus.ACTIVE, accountantTest);
        client2 = new Client(2L, RegistrationType.CNPJ, "12345678000199", "124", "Test Client 2", "Test Fantasy Name 2", now, CompanyStatus.INACTIVE, accountantTest);
        clientDTO = new ClientDTO(1L, RegistrationType.CPF, "29563355024", "123", "Test Client", "Test Fantasy Name", now, CompanyStatus.ACTIVE, 1L);
        clientList = Arrays.asList(clientTest, client2);
    }

    @DisplayName("Given ClientDTO when convertToClient then return Client")
    @Test
    void testGivenClientDTO_whenConvertToClient_thenReturnClient() throws AccountantNotFoundException {
        // Arrange
        when(accountantRepository.findById(clientDTO.getAccountantId())).thenReturn(Optional.of(accountantTest));

        // Act
        Client client = clientService.convertToClient(clientDTO);

        // Assert
        assertEquals(clientDTO.getRegistrationType(), client.getRegistrationType());
        assertEquals(clientDTO.getRegistrationNumber(), client.getRegistrationNumber());
        assertEquals(clientDTO.getClientCode(), client.getClientCode());
        assertEquals(clientDTO.getName(), client.getName());
        assertEquals(clientDTO.getFantasyName(), client.getFantasyName());
        assertEquals(clientDTO.getRegistrationDate(), client.getRegistrationDate());
        assertEquals(clientDTO.getCompanyStatus(), client.getCompanyStatus());
        assertEquals(clientDTO.getAccountantId(), client.getAccountant().getId());
    }

    @DisplayName("Given AccountantId null when convertToClient then throw AccountantNotFoundException")
    @Test
    void testGivenAccountantIdNull_whenConvertToClient_thenThrowAccountantNotFoundException() {
        // Arrange
        clientDTO.setAccountantId(null);

        // Act & Assert
        AccountantNotFoundException exception = assertThrows(AccountantNotFoundException.class, () -> {
            clientService.convertToClient(clientDTO);
        });
        assertEquals("Você deve informar um contador válido", exception.getMessage());
        verify(accountantRepository, never()).findById(anyLong());
    }

    @DisplayName("Given AccountantId that does'nt exist on the database when convertToClient then throw AccountantNotFoundException")
    @Test
    void testGivenAccountantIdThatDoesNotExist_whenConvertToClient_thenThrowAccountantNotFoundException() {
        // Arrange
        Long accountantId = 999L;
        clientDTO.setAccountantId(accountantId);
        when(accountantRepository.findById(accountantId)).thenReturn(Optional.empty());

        // Act & Assert
        AccountantNotFoundException exception = assertThrows(AccountantNotFoundException.class, () -> {
            clientService.convertToClient(clientDTO);
        });
        assertEquals("Contador não encontrado com o id: " + accountantId, exception.getMessage());
        verify(accountantRepository, times(1)).findById(accountantId);
    }

    @DisplayName("Given name and pageable when findClientsOrderedByName then return Client Page")
    @Test
    void testGivenNameAndPageable_whenFindClientsOrderedByName_thenReturnClientPage() {
        // Arrange
        String name = "Test Client";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Client> clientPage = new PageImpl<>(clientList, pageable, clientList.size());
        when(clientRepository.findClientsByName(name, pageable)).thenReturn(clientPage);

        // Act
        Page<Client> result = clientService.findClientsOrderedByName(name, pageable);

        // Assert
        assertEquals(clientPage, result);
        assertEquals(2, result.getTotalElements());
        assertEquals(clientList, result.getContent());
        verify(clientRepository, times(1)).findClientsByName(name, pageable);
    }

    @DisplayName("Given id when findClientById then return Client")
    @Test
    void testGivenId_whenFindClientById_thenReturnClient() {
        // Arrange
        Long id = 1L;
        given(clientRepository.findById(id)).willReturn(Optional.of(clientTest));

        // Act
        Optional<Client> result = clientService.findClientById(id);

        // Assert
        assertEquals(Optional.of(clientTest), result);
        verify(clientRepository, times(1)).findById(id);
    }

    @DisplayName("Given Client when addClient then return Client")
    @Test
    void testGivenClient_whenAddClient_thenReturnClient() throws AccountantNotFoundException {
        // Arrange
        given(accountantRepository.findById(clientDTO.getAccountantId())).willReturn(Optional.of(accountantTest));
        given(clientRepository.save(any(Client.class))).willReturn(clientTest);

        // Act
        Client result = clientService.addClient(clientService.convertToClient(clientDTO));

        // Assert
        assertEquals(clientTest, result);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @DisplayName("Given Client with null Accountant when addClient then save Client without Accountant")
    @Test
    void testGivenClientWithNullAccountant_whenAddClient_thenSaveClientWithoutAccountant() throws AccountantNotFoundException {
        // Arrange
        clientTest.setAccountant(null);

        // Act
        clientService.addClient(clientTest);

        // Assert
        verify(clientRepository, times(1)).save(clientTest);
    }

    @DisplayName("Given Client with Accountant having null ID when addClient then save Client without Accountant")
    @Test
    void testGivenClientWithAccountantHavingNullID_whenAddClient_thenSaveClientWithoutAccountant() throws AccountantNotFoundException {
        // Arrange
        Accountant accountantWithNullID = new Accountant();
        clientTest.setAccountant(accountantWithNullID);

        // Act
        clientService.addClient(clientTest);

        // Assert
        verify(clientRepository, times(1)).save(clientTest);
    }


    @DisplayName("Given Accountant with non-existing ID when addClient then throw AccountantNotFoundException")
    @Test
    void testGivenAccountantWithNonExistingID_whenAddClient_thenThrowAccountantNotFoundException() {
        // Arrange
        Accountant accountantWithNonExistingID = new Accountant();
        accountantWithNonExistingID.setId(999L); // ID que não existe no banco
        Client clientWithNonExistingAccountant = new Client();
        clientWithNonExistingAccountant.setAccountant(accountantWithNonExistingID);

        when(accountantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        AccountantNotFoundException exception = assertThrows(AccountantNotFoundException.class, () -> {
            clientService.addClient(clientWithNonExistingAccountant);
        });

        assertEquals("Contador não encontrado com o id: 999", exception.getMessage());
        verify(accountantRepository, times(1)).findById(999L);
        verify(clientRepository, never()).save(any(Client.class));
    }

    @DisplayName("Given id and ClientDTO when updateClient then return updated Client")
    @Test
    void testGivenIdAndClientDTO_whenUpdateClient_thenReturnUpdatedClient() throws ClientNotFoundException, AccountantNotFoundException {
        // Arrange
        Long clientId = 1L;
        given(clientRepository.findById(clientId)).willReturn(Optional.of(clientTest));
        given(clientRepository.save(any(Client.class))).willReturn(clientTest);
        given(accountantRepository.findById(clientDTO.getAccountantId())).willReturn(Optional.of(accountantTest));

        // Act
        Client result = clientService.updateClient(clientId, clientDTO);

        // Assert
        assertEquals(clientTest, result);
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(clientTest);
    }

    @DisplayName("Given id that does not exist when updateClient then throw ClientNotFoundException")
    @Test
    void testGivenIdThatDoesNotExist_whenUpdateClient_thenThrowClientNotFoundException() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, () -> {
            clientService.updateClient(clientId, clientDTO);
        });
        assertEquals("Cliente não encontrado com o id: " + clientId, exception.getMessage());
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, never()).save(any(Client.class));
    }

    @DisplayName("Given ClientDTO with null AccountantId when updateClient then update Client without Accountant")
    @Test
    void testGivenClientDTOWithNullAccountantId_whenUpdateClient_thenUpdateClientWithoutAccountant() throws AccountantNotFoundException, ClientNotFoundException {
        // Arrange
        Long clientId = 1L;
        clientDTO.setAccountantId(null);

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientTest));
        when(clientRepository.save(any(Client.class))).thenReturn(clientTest);

        // Act
        Client result = clientService.updateClient(clientId, clientDTO);

        // Assert
        assertEquals(clientTest, result);
        verify(clientRepository, times(1)).findById(clientId);
        verify(clientRepository, times(1)).save(clientTest);
        verify(accountantRepository, never()).findById(anyLong());
    }

    @DisplayName("Given ClientDTO with non-existing AccountantId when updateClient then throw AccountantNotFoundException")
    @Test
    void testGivenClientDTOWithNonExistingAccountantId_whenUpdateClient_thenThrowAccountantNotFoundException() {
        // Arrange
        Long clientId = 1L;
        clientDTO.setAccountantId(999L); // ID que não existe no banco

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientTest));
        when(accountantRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        AccountantNotFoundException exception = assertThrows(AccountantNotFoundException.class, () -> {
            clientService.updateClient(clientId, clientDTO);
        });

        assertEquals("Contador não encontrado com o id: 999", exception.getMessage());
        verify(clientRepository, times(1)).findById(clientId);
        verify(accountantRepository, times(1)).findById(999L);
        verify(clientRepository, never()).save(any(Client.class));
    }

    @DisplayName("Given id that exists in database when deleteClient then delete successfully")
    @Test
    void testGivenIdThatExists_whenDeleteClient_thenDeleteSuccessfully() throws ClientNotFoundException {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> clientService.deleteClient(clientId));

        // Assert
        verify(clientRepository, times(1)).existsById(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @DisplayName("Given id that doesn't exist in database when deleteClient then throw ClientNotFoundException")
    @Test
    void testGivenNonExistingId_whenDeleteClient_thenThrowClientNotFoundException() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.existsById(clientId)).thenReturn(false);

        // Act & Assert
        ClientNotFoundException exception = assertThrows(ClientNotFoundException.class,
                () -> clientService.deleteClient(clientId));
        assertEquals("Cliente não encontrado com o id: " + clientId, exception.getMessage());
        verify(clientRepository, times(1)).existsById(clientId);
        verify(clientRepository, never()).deleteById(clientId);
    }
}
