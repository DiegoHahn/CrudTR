package crude.tr.cadastroclientes.controller;

import crude.tr.cadastroclientes.Exceptions.*;
import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
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

    public AccountantController(AccountantService accountantService) {
        this.accountantService = accountantService;
    }

    @GetMapping
    public ResponseEntity<Page<Accountant>> listAccountants(
            @RequestParam(required = false) String name,
            @RequestParam int page,
            @RequestParam int size) {
        //Criando um objeto Pageable com os parametros passados, vai transformar em page e size que é o que o JPA precisa
        Pageable pageable = PageRequest.of(page, size);
        Page<Accountant> accountantPage = accountantService.findAccountantsOrderedByName(name, pageable);
        return new ResponseEntity<>(accountantPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Optional<Accountant> getAccountantById(@PathVariable Long id) {
        return accountantService.findAccountantByID(id); // Buscando um cliente pelo id
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Accountant>> addAccountant(@RequestBody @Valid AccountantDTO accountantDTO) {
        try {
            Accountant accountant = accountantService.convertToAccountant(accountantDTO);
            Accountant savedAccountant = accountantService.addAccountant(accountant);
            ApiResponse<Accountant> response =new ApiResponse<>(savedAccountant, "Contador salvo com sucesso");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateAccountantException e) {
            ApiResponse<Accountant> response =new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Accountant>> updateAccountant(@PathVariable Long id, @RequestBody @Valid AccountantDTO accountantDTO) {
        try {
            Accountant updatedAccountant = accountantService.updateAccountant(id, accountantDTO);
            ApiResponse<Accountant> response = new ApiResponse<>(updatedAccountant, "Contador atualizado com sucesso");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AccountantNotFoundException e) {
            ApiResponse<Accountant> response = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Accountant>> deleteAccountant(@PathVariable Long id) {
        try {
            accountantService.deleteAccountant(id);
            ApiResponse<Accountant> response = new ApiResponse<>(null, "Contador excluído com sucesso");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (AccountantNotFoundException e) {
            ApiResponse<Accountant> response = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (ForeignKeyViolationException e) {
            ApiResponse<Accountant> response = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (DeleteAccountantException e) {
            ApiResponse<Accountant> response = new ApiResponse<>(null, e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



