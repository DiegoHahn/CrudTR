package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
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

    public Optional<Accountant> findAccountantByID(Long id) {
        return accountantRepository.findById(id);
    }

    public List<Accountant> findAllAccountants() {
        return accountantRepository.findAllByOrderByNameAsc();
    }

   public ResponseEntity<Accountant> addAccountant(Accountant accountant) {
    try {
        Optional<Accountant> existingAccountant = accountantRepository.findByRegistrationNumber(accountant.getRegistrationNumber());
        if (existingAccountant.isPresent()) {
            throw new RuntimeException("Registro duplicado");
        } else {
            return new ResponseEntity<>(accountantRepository.save(accountant), HttpStatus.OK);
        }
    } catch (RuntimeException e) {
        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CONFLICT);
    }
}

    public ResponseEntity<Accountant> updateAccountant(Long id, AccountantDTO accountantDTO) {
        Optional<Accountant> accountantOptional = findAccountantByID(id);
        if (accountantOptional.isPresent()) {
            Accountant accountant = convertToAccountant(accountantDTO);
            return new ResponseEntity<>(accountantRepository.save(accountant), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteAccountant(Long id) {
        Optional<Accountant> accountantOptional = findAccountantByID(id);
        try {
            if (accountantOptional.isPresent()) {
                accountantRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DataIntegrityViolationException e) {
            //pega a causa raiz da exceção para verificar se é uma exceção de violação de chave estrangeira
            Throwable cause = e.getRootCause();
            if (cause instanceof SQLException) {
                SQLException sqlException = (SQLException) cause;
                if ("23503".equals(sqlException.getSQLState())) {
                    return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
