package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import crude.tr.cadastroclientes.service.AccountantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accountants")
public class AccountantController {

    private final AccountantService accountantService;
    private final AccountantRepository accountantRepository;

    public AccountantController(AccountantService accountantService, AccountantRepository accountantRepository) {
        this.accountantService = accountantService;
        this.accountantRepository = accountantRepository;
    }

    @PostMapping
    public ResponseEntity<Accountant> addAccountant(@RequestBody AccountantDTO accountantdto) {
        Accountant accountant = accountantService.convertToAccountant(accountantdto);
        Accountant savedAccountant = accountantService.addAccountant(accountant);
        return new ResponseEntity<>(savedAccountant, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Accountant> getAllAccountants() {
        return accountantRepository.findAllAccountantsOrderedByName(); // Buscando todos os clientes
    }

    @GetMapping("/{id}")
    public Optional<Accountant> getAccountantById(@PathVariable Long id) {
        return accountantRepository.findById(id); // Buscando um cliente pelo id
    }

    @PutMapping("/{id}")
    //PathVariable = id será passado na URL e RequestBody = corpo da requisição será um objeto cliente
    public ResponseEntity<Accountant> updateAccountant(@PathVariable Long id, @RequestBody AccountantDTO accountantDTO) {
       return accountantService.updateAccountant(id, accountantDTO);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountant(@PathVariable Long id) {
        return accountantService.deleteAccountant(id);
    }
}



