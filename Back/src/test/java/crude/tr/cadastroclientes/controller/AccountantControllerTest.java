package crude.tr.cadastroclientes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
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
        accountantTeste = new Accountant(1L, "12345678901", "1123", "Contador1", true);
        accountantDTO = new AccountantDTO(1L, "12345678901", "1123", "Contador1", true);
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
//                .andDo(print())
                .andExpect((ResultMatcher) jsonPath("$.content.size()", is(1)))
                .andExpect(jsonPath("$.content[0].registrationNumber").value(accountantTeste.getRegistrationNumber()));
    }

    @DisplayName("Given Id when getAccountantById then return C")
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

    @DisplayName("Given Id and AccountantDTO when updateAccountant then return Accountant")
    @Test
    public void testGivenIdAndAccountantDTO_whenUpdateAccountant_thenReturnAccountant() throws Exception {
        // Arrange
        given(accountantService.updateAccountant(1L, accountantDTO))
                .willReturn(new ResponseEntity<>(accountantTeste, HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(put("/accountants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountantDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(accountantTeste.getId().intValue())))
                .andExpect(jsonPath("$.registrationNumber", is(accountantTeste.getRegistrationNumber())));

    }


    @DisplayName("Given Id when deleteAccountant throws Exception then return Internal Server Error")
    @Test
    public void testGivenId_whenDeleteAccountantThrowsException_thenReturnInternalServerError() throws Exception {
        // Arrange
        given(accountantService.deleteAccountant(1L))
                .willThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        mockMvc.perform(delete("/accountants/1"))
                .andExpect(status().isInternalServerError());
    }
}
