package crude.tr.cadastroclientes.repository;

import crude.tr.cadastroclientes.model.Accountant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountantRepositoryTest {
    @Autowired
    private AccountantRepository accountantRepository;

    private Accountant accountant;

    @BeforeEach
    public void setup() {
        accountant = new Accountant("123", "12345678901", "Antonio", true);
    }

    @DisplayName("Given Accountant object when save then return saved Accountant")
    @Test
    void testGivenAccountantObject_whenSave_thenReturnSavedAccountant() {

        //Act
        Accountant savedAccountant = accountantRepository.save(accountant);

        //Assert
        assertNotNull(savedAccountant);
        assertTrue(savedAccountant.getId() > 0);
    }

    @DisplayName("Given Accountant list when findAccountantsByName then return Accountant page")
    @Test
    void testGivenAccountantList_whenFindAccountantsByName_thenReturnAccountantResponse() {

        //Arrange
        Accountant accountant1 = new Accountant("123", "12345678901", "João", true);
        accountantRepository.save(accountant1);
        accountantRepository.save(accountant);

        //Act
        Page<Accountant> accountants = accountantRepository.findAccountantsByName("", null);

        //Assert
        assertNotNull(accountants);
        assertEquals(2, accountants.getTotalElements());
        assertEquals("Antonio", accountants.getContent().get(0).getName()); //Testando a ordenação
    }

    @DisplayName("Given Accountant list when findByRegistrationNumber then return Accountant optional")
    @Test
    void testGivenAccountantList_whenFindByRegistrationNumber_thenReturnAccountantOptional() {
        //Arrange
        accountantRepository.save(accountant);

        //Act
        Optional<Accountant> accountant = accountantRepository.findByRegistrationNumber("123");

        //Assert
        assertNotNull(accountant);
        assertTrue(accountant.isPresent());
    }

    @DisplayName("Given Accountant when update Accountant then return updated Accountant")
    @Test
    void testGivenAccountant_whenUpdatedAccountant_thenReturnUpdatedAccountant() {

        //Arrange
        accountantRepository.save(accountant);

        //Act
        Optional<Accountant> savedAccountant = accountantRepository.findById(accountant.getId());
        savedAccountant.get().setName("Teste 2");
        Accountant updatedAccountant = accountantRepository.save(savedAccountant.get());

        //Assert
        assertNotNull(updatedAccountant);
        assertEquals("Teste 2", updatedAccountant.getName());
    }
}
