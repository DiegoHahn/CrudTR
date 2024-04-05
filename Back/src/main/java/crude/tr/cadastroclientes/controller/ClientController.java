package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.repository.ClientRepository;
import crude.tr.cadastroclientes.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> addClient(@RequestBody ClientDTO clientDTO) {
            Client client = clientService.convertToClient(clientDTO);
            Client savedClient = clientService.addClient(client);
            return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping
        public List<Client> getCliente() {
            return clientRepository.findAll();
        }

        @GetMapping("/{id}")
        public Optional<Client> getClienteById(@PathVariable Long id) {
            return clientRepository.findById(id);
        }
}
