package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import crude.tr.cadastroclientes.service.AccountantService;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Accountant> addAccountant(@RequestBody @Valid AccountantDTO accountantdto) {
        Accountant accountant = accountantService.convertToAccountant(accountantdto);
        Accountant savedAccountant = accountantService.addAccountant(accountant);
        return new ResponseEntity<>(savedAccountant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Accountant>> listAccountants(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int pageIndex,
            @RequestParam(defaultValue = "10") int pageSize) {

        //Criando um objeto Pageable com os parametros passados, vai transformar em page e size que é o que o JPA precisa
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Page<Accountant> accountantPage = accountantService.findAccountantsOrderedByName(name, pageable);
        return new ResponseEntity<>(accountantPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Accountant> getAccountantById(@PathVariable Long id) {
        return accountantRepository.findById(id); // Buscando um cliente pelo id
    }

    @PutMapping("/{id}")
    //PathVariable = id será passado na URL e RequestBody = corpo da requisição será um objeto cliente
    public ResponseEntity<Accountant> updateAccountant(@PathVariable Long id, @RequestBody @Valid AccountantDTO accountantDTO) {
       return accountantService.updateAccountant(id, accountantDTO);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccountant(@PathVariable Long id) {
        return accountantService.deleteAccountant(id);
    }
}



