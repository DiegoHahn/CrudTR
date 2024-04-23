package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.model.Exceptions;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountantService {
    private final AccountantRepository accountantRepository;


    @Autowired
    public AccountantService(AccountantRepository accountantRepository) {
        this.accountantRepository = accountantRepository;
    }

    public Accountant convertToAccountant(AccountantDTO accountantDTO) {
        Accountant accountant = new Accountant();
        accountant.setId(accountantDTO.getId());
        accountant.setRegistrationNumber(accountantDTO.getRegistrationNumber());
        accountant.setAccountantCode(accountantDTO.getAccountantCode());
        accountant.setName(accountantDTO.getName());
        accountant.setIsActive(accountantDTO.getIsActive());
        return accountant;
    }

    //Usando o Pageable para fazer a paginação em vez de passar os parametros manualmente
    public Page<Accountant> findAccountantsOrderedByName(String name, Pageable pageable) {
        return accountantRepository.findAccountantsByName(name, pageable);
    }

    public Optional<Accountant> seachAccountantByID(Long id) {
        return accountantRepository.findById(id);
    }

    public Accountant addAccountant(Accountant accountant) {
        try {//if
            return accountantRepository.save(accountant);
        } catch (DataIntegrityViolationException e) {
            throw new Exceptions("Registro duplicado");        }
    }

    public ResponseEntity<Accountant> updateAccountant(Long id, AccountantDTO accountantDTO) {
        Optional<Accountant> accountantOptional = seachAccountantByID(id);
        if (accountantOptional.isPresent()) {
            Accountant accountant = convertToAccountant(accountantDTO);
            return new ResponseEntity<>(accountantRepository.save(accountant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteAccountant(Long id) {
        Optional<Accountant> accountantOptional = seachAccountantByID(id);
        if (accountantOptional.isPresent()) {
            accountantRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
