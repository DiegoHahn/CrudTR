package crude.tr.cadastroclientes.repository;

import crude.tr.cadastroclientes.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
