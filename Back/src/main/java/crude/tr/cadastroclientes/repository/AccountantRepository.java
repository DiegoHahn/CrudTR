package crude.tr.cadastroclientes.repository;

import crude.tr.cadastroclientes.model.Accountant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountantRepository extends JpaRepository<Accountant, Long> {
}
