package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.Exceptions.AccountantNotFoundException;
import crude.tr.cadastroclientes.Exceptions.DeleteAccountantException;
import crude.tr.cadastroclientes.Exceptions.DuplicateAccountantException;
import crude.tr.cadastroclientes.Exceptions.ForeignKeyViolationException;
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

    public Accountant addAccountant(Accountant accountant) throws DuplicateAccountantException {
        Optional<Accountant> existingAccountant = accountantRepository.findByRegistrationNumber(accountant.getRegistrationNumber());
        if (existingAccountant.isPresent()) {
            throw new DuplicateAccountantException("Registro duplicado");
        } else {
            return accountantRepository.save(accountant);
        }
    }

    public ResponseEntity<Accountant> updateAccountant(Long id, AccountantDTO accountantDTO) {
        Optional<Accountant> accountantOptional = findAccountantByID(id);
        if (accountantOptional.isPresent()) {
            Accountant accountantToUpdate = accountantOptional.get();
            accountantToUpdate.setRegistrationNumber(accountantDTO.getRegistrationNumber());
            accountantToUpdate.setAccountantCode(accountantDTO.getAccountantCode());
            accountantToUpdate.setName(accountantDTO.getName());
            accountantToUpdate.setIsActive(accountantDTO.getIsActive());
            Accountant updatedAccountant = accountantRepository.save(accountantToUpdate);
            return new ResponseEntity<>(updatedAccountant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteAccountant(Long id) throws DeleteAccountantException, AccountantNotFoundException, ForeignKeyViolationException {
        Optional<Accountant> accountantOptional = findAccountantByID(id);
        if (accountantOptional.isPresent()) {
            try {
                accountantRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (DataIntegrityViolationException e) {
                Throwable cause = e.getRootCause();
                if (cause instanceof SQLException) {
                    throw new ForeignKeyViolationException("Você não pode excluir um contador que está associado a um cliente");
                }
                throw new DeleteAccountantException("Não foi possivel excluir esse contador");
            }
        }  else {
            throw new AccountantNotFoundException("Contador não encontrado com o id: " + id);
        }
    }
}
