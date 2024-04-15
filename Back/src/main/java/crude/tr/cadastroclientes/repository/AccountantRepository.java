package crude.tr.cadastroclientes.repository;

import crude.tr.cadastroclientes.model.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountantRepository extends JpaRepository<Accountant, Long> {
    @Query("SELECT a FROM Accountant a ORDER BY a.name ASC")
    List<Accountant> findAllAccountantsOrderedByName();
}
