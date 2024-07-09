package crude.tr.cadastroclientes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crude.tr.cadastroclientes.Exceptions.DuplicateAccountantException;
import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.service.AccountantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountantController.class)
public class AccountantControllerTest {

    //Objeto que vai simular requisições HTTP
    @Autowired
    private MockMvc mockMvc;

    //Objeto que vai desserializar valores em JSON
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AccountantService accountantService;

    private Accountant accountantTeste;
    private AccountantDTO accountantDTO;

    @BeforeEach
    public void setUp() {
        accountantTeste = new Accountant(1L, "47048010045", "1123", "Contador1", true);
        accountantDTO = new AccountantDTO(1L, "47048010045", "1123", "Contador1", true);
    }

    @DisplayName("Given name, page, and size when listAccountants then return Accountant Page")
    @Test
    public void testGivenNamePageSize_whenListAccountants_thenReturnAccountantPage() throws Exception {
        // Arrange
        given(accountantService.findAccountantsOrderedByName("Contador1", PageRequest.of(0, 10)))
                .willReturn(new PageImpl<>(Collections.singletonList(accountantTeste)));

        // Act & Assert
        mockMvc.perform(get("/accountants")
                        .param("name", "Contador1")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.content.size()", is(1)))
                .andExpect(jsonPath("$.content[0].registrationNumber").value(accountantTeste.getRegistrationNumber()));
    }

    @DisplayName("Given Id when getAccountantById then return Accountant Optional")
    @Test
    public void testGivenId_whenGetAccountantsById_thenReturnAccountantOptional() throws Exception {
        //Arrange
        long id = 1L;
        given(accountantService.findAccountantByID(1L))
                .willReturn(Optional.of(accountantTeste));

        // Act & Assert
        mockMvc.perform(get("/accountants/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(accountantTeste.getId().intValue())));
    }

    @DisplayName("Given valid AccountantDTO when addAccountant then return Created")
    @Test
    public void testGivenValidAccountantDTO_whenAddAccountant_thenReturnCreated() throws Exception {
        // Arrange
        given(accountantService.convertToAccountant(accountantDTO)).willReturn(accountantTeste);
        given(accountantService.addAccountant(accountantTeste)).willReturn(new ResponseEntity<>(accountantTeste, HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(post("/accountants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(accountantTeste.getId().intValue())))
                .andExpect(jsonPath("$.registrationNumber", is(accountantTeste.getRegistrationNumber())))
                .andExpect(jsonPath("$.accountantCode", is(accountantTeste.getAccountantCode())))
                .andExpect(jsonPath("$.name", is(accountantTeste.getName())))
                .andExpect(jsonPath("$.isActive", is(accountantTeste.getIsActive())));
    }


    @DisplayName("Given duplicate AccountantDTO when addAccountant then return Conflict")
    @Test
    public void testGivenDuplicateAccountantDTO_whenAddAccountant_thenReturnConflict() throws Exception {
        // Arrange
        given(accountantService.convertToAccountant(accountantDTO)).willReturn(accountantTeste);
        given(accountantService.addAccountant(accountantTeste)).willThrow(new DuplicateAccountantException("Registro duplicado"));

        // Act & Assert
        mockMvc.perform(post("/accountants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountantDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testObjectMapperSerialization() throws Exception {
        String json = objectMapper.writeValueAsString(accountantDTO);
        AccountantDTO deserialized = objectMapper.readValue(json, AccountantDTO.class);
        assertEquals(accountantDTO, deserialized);
    }

    @DisplayName("Given Id and AccountantDTO when updateAccountant then return Accountant")
    @Test
    public void testGivenIdAndAccountantDTO_whenUpdateAccountant_thenReturnAccountant() throws Exception {
        // Arrange
        given(accountantService.updateAccountant(1L, accountantDTO))
                .willReturn(new ResponseEntity<>(accountantTeste, HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(put("/accountants/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountantDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(accountantTeste.getId().intValue())))
                .andExpect(jsonPath("$.registrationNumber", is(accountantTeste.getRegistrationNumber())))
                .andExpect(jsonPath("$.accountantCode", is(accountantTeste.getAccountantCode())))
                .andExpect(jsonPath("$.name", is(accountantTeste.getName())))
                .andExpect(jsonPath("$.isActive", is(accountantTeste.getIsActive())));
    }


    @DisplayName("Given id when deleteAccountant then return NO_CONTENT")
    @Test
    public void testGivenId_whenDeleteAccountant_thenReturnNoContent() throws Exception {
        // Arrange
        Long id = 1L;
        when(accountantService.deleteAccountant(id))
                .thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        // Act & Assert
        mockMvc.perform(delete("/accountants/{id}", id))
                .andExpect(status().isNoContent());

        verify(accountantService, times(1)).deleteAccountant(id);
    }

    @DisplayName("Given non-existing id when deleteAccountant then return NOT_FOUND")
    @Test
    public void testGivenNonExistingId_whenDeleteAccountant_thenReturnNotFound() throws Exception {
        // Arrange
        Long id = 999L;
        when(accountantService.deleteAccountant(id))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(delete("/accountants/{id}", id))
                .andExpect(status().isNotFound());

        verify(accountantService, times(1)).deleteAccountant(id);
    }

    @DisplayName("Given id with foreign key constraint when deleteAccountant then return CONFLICT")
    @Test
    public void testGivenIdWithForeignKeyConstraint_whenDeleteAccountant_thenReturnConflict() throws Exception {
        // Arrange
        Long id = 1L;
        when(accountantService.deleteAccountant(id))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONFLICT));

        // Act & Assert
        mockMvc.perform(delete("/accountants/{id}", id))
                .andExpect(status().isConflict());

        verify(accountantService, times(1)).deleteAccountant(id);
    }
}
