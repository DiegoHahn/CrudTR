package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.Exceptions.AccountantNotFoundException;
import crude.tr.cadastroclientes.Exceptions.ApiResponse;
import crude.tr.cadastroclientes.Exceptions.ClientNotFoundException;
import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Page<Client>> listClients(
            @RequestParam(required = false) String name,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clientPage = clientService.findClientsOrderedByName(name, pageable);
        return new ResponseEntity<>(clientPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Client> getClienteById(@PathVariable Long id) {
        return clientService.findClientById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Client>> addClient(@RequestBody @Valid ClientDTO clientDTO) {
        try {
            Client client = clientService.convertToClient(clientDTO);
            Client savedClient = clientService.addClient(client);
            ApiResponse<Client> response = new ApiResponse<>(savedClient, "Cliente salvo com sucesso");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AccountantNotFoundException e) {
            ApiResponse<Client> response = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> updateClient(@PathVariable Long id, @RequestBody @Valid ClientDTO clientDTO) {
        try {
            Client updatedClient = clientService.updateClient(id, clientDTO);
            ApiResponse<Client> response = new ApiResponse<>(updatedClient, "Cliente atualizado com sucesso");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ClientNotFoundException | AccountantNotFoundException e) {
            ApiResponse<Client> response = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Client>> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
            ApiResponse<Client> response = new ApiResponse<>(null, "Cliente excluído com sucesso");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (ClientNotFoundException e) {
            ApiResponse<Client> response = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
