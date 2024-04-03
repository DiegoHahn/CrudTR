package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accountants")
public class AccountantController {

        @Autowired
        private AccountantRepository accountantRepository;

        @PostMapping
        public Accountant addAccountant(@RequestBody Accountant accountant) {
            return accountantRepository.save(accountant); // Salvando um novo cliente com save (método da JPA)
        }

        @GetMapping
        public List<Accountant> getAllAccountants() {
            return accountantRepository.findAll(); // Buscando todos os clientes
        }

        @GetMapping("/{id}")
        public Optional<Accountant> getAccountantById(@PathVariable Long id) {
            return accountantRepository.findById(id); // Buscando um cliente pelo id
        }
}


