package crude.tr.cadastroclientes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import crude.tr.cadastroclientes.Exceptions.AccountantNotFoundException;
import crude.tr.cadastroclientes.Exceptions.ClientNotFoundException;
import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.model.CompanyStatus;
import crude.tr.cadastroclientes.model.RegistrationType;
import crude.tr.cadastroclientes.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;

import static crude.tr.cadastroclientes.model.CompanyStatus.*;
import static crude.tr.cadastroclientes.model.RegistrationType.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientService;

    private Accountant accountantTest;
    private Client clientTest;
    private ClientDTO clientDTO;

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();
        accountantTest = new Accountant(1L, "47048010045", "1123", "Contador1", true);
        clientTest = new Client(1L, RegistrationType.CPF, "29563355024", "123", "Test Client", "Test Fantasy Name", now, ACTIVE, accountantTest);
        clientDTO = new ClientDTO(1L, RegistrationType.CPF, "29563355024", "123", "Test Client", "Test Fantasy Name", now, CompanyStatus.ACTIVE, 1L);

    }

    @DisplayName("Given name, page, and size when listClients then return Client Page")
    @Test
    public void testGivenNamePageSize_whenListClients_thenReturnClientPage() throws Exception {
        given(clientService.findClientsOrderedByName("Test Client", PageRequest.of(0, 10)))
                .willReturn(new PageImpl<>(Collections.singletonList(clientTest)));

        mockMvc.perform(get("/clients")
                        .param("name", "Test Client")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)))
                .andExpect(jsonPath("$.content[0].name").value(clientTest.getName()));
    }

    @DisplayName("Given Id when getClientById then return Client")
    @Test
    public void testGivenId_whenGetClientById_thenReturnClient() throws Exception {
        long id = 1L;
        given(clientService.findClientById(1L))
                .willReturn(Optional.of(clientTest));

        mockMvc.perform(get("/clients/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(clientTest.getId().intValue())));
    }

    @DisplayName("Given Id and ClientDTO when updateClient then return Client")
    @Test
    public void testGivenIdAndClientDTO_whenUpdateClient_thenReturnClient() throws Exception {
        Long id = 1L;
        when(clientService.updateClient(id, clientDTO))
                .thenReturn(clientTest);

        mockMvc.perform(put("/clients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.data.id", is(clientTest.getId().intValue())))
//                .andExpect(jsonPath("$.data.name", is(clientTest.getName())))
//                .andExpect(jsonPath("$.message", is("Cliente atualizado com sucesso")));
    }

    @Test
    public void testGivenNonExistingIdAndClientDTO_whenUpdateClient_thenReturnNotFound() throws Exception {
        Long id = 999L;
        when(clientService.updateClient(eq(id), any(ClientDTO.class)))
                .thenThrow(new ClientNotFoundException("Cliente não encontrado com o id: " + id));

        mockMvc.perform(put("/clients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Cliente não encontrado com o id: " + id));
    }

    @DisplayName("Given id when deleteClient then return NO_CONTENT")
    @Test
    public void testGivenId_whenDeleteClient_thenReturnNoContent() throws Exception {
        Long id = 1L;
        doNothing().when(clientService).deleteClient(id);

        mockMvc.perform(delete("/clients/{id}", id))
                .andExpect(status().isNoContent());

        verify(clientService, times(1)).deleteClient(id);
    }

    @DisplayName("Given non-existing id when deleteClient then return NOT_FOUND")
    @Test
    public void testGivenNonExistingId_whenDeleteClient_thenReturnNotFound() throws Exception {
        Long id = 999L;
        doThrow(new ClientNotFoundException("Cliente não encontrado com o id: " + id))
                .when(clientService).deleteClient(id);

        mockMvc.perform(delete("/clients/{id}", id))
                .andExpect(status().isNotFound());

        verify(clientService, times(1)).deleteClient(id);
    }

    @DisplayName("Given valid ClientDTO when addClient then return Created")
    @Test
    public void testGivenValidClientDTO_whenAddClient_thenReturnCreated() throws Exception {
        given(clientService.convertToClient(clientDTO)).willReturn(clientTest);
        given(clientService.addClient(clientTest)).willReturn(clientTest);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientDTO)))
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.id", is(clientTest.getId().intValue())))
//                .andExpect(jsonPath("$.data.name", is(clientTest.getName())))
                .andExpect(jsonPath("$.message", is("Cliente salvo com sucesso")));
    }

    @Test
    public void testObjectMapperSerialization() throws Exception {
        String json = objectMapper.writeValueAsString(clientDTO);
        ClientDTO deserialized = objectMapper.readValue(json, ClientDTO.class);
        assertEquals(clientDTO, deserialized);
    }

}