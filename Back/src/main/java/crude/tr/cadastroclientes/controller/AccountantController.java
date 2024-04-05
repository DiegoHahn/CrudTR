package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.dto.ClientDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Client;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import crude.tr.cadastroclientes.service.AccountantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accountants")
public class AccountantController {

    private final AccountantService accountantService;

    @Autowired
    private AccountantRepository accountantRepository;

    public AccountantController(AccountantService accountantService) {
        this.accountantService = accountantService;
    }

    @PostMapping
    public ResponseEntity<Accountant> addAccountant(@RequestBody AccountantDTO accountantdto) {
        Accountant accountant = accountantService.convertToAccountant(accountantdto);
        Accountant savedAccountant = accountantService.addAccountant(accountant);
        return new ResponseEntity<>(savedAccountant, HttpStatus.CREATED);
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


