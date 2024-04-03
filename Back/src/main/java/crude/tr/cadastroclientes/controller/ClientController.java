package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

        @Autowired
        private ClientRepository clientRepository;

        @PostMapping
        public Client addCliente(@RequestBody Client cliente) {
            return clientRepository.save(cliente);
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
