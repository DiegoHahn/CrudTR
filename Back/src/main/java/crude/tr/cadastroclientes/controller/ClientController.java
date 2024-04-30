package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.repository.ClientRepository;
import crude.tr.cadastroclientes.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
        return clientService.findCLientById(id);
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody ClientDTO clientDTO) {
            Client client = clientService.convertToClient(clientDTO);
            Client savedClient = clientService.addClient(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }

}
