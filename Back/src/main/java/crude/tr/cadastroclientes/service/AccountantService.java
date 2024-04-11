package crude.tr.cadastroclientes.service;

import crude.tr.cadastroclientes.dto.AccountantDTO;
import crude.tr.cadastroclientes.model.Accountant;
import crude.tr.cadastroclientes.repository.AccountantRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        accountant.setRegistrationNumber(accountantDTO.getRegistrationNumber());
        accountant.setAccountantCode(accountantDTO.getAccountantCode());
        accountant.setName(accountantDTO.getName());
        accountant.setIsActive(accountantDTO.getIsActive());
        return accountant;
    }

    public Optional<Accountant> seachAccountantByID(Long id) {
        return accountantRepository.findById(id);
    }

    public Accountant addAccountant(Accountant accountant) {
        return accountantRepository.save(accountant);
    }
}
