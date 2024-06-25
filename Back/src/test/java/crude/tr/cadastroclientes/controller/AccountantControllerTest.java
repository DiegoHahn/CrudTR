package crude.tr.cadastroclientes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    public void setUp() {
        accountantTeste = new Accountant(1L, "12345678901", "1123", "Contador1", true);
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
                .andExpect(jsonPath("$.content[0].id").value(accountantTeste.getId()))
                .andExpect(jsonPath("$.content[0].registrationNumber").value(accountantTeste.getRegistrationNumber()))
                .andExpect(jsonPath("$.content[0].accountantCode").value(accountantTeste.getAccountantCode()))
                .andExpect(jsonPath("$.content[0].name").value(accountantTeste.getName()))
                .andExpect(jsonPath("$.content[0].isActive").value(accountantTeste.getIsActive()));
    }

}
