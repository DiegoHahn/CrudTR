package crude.tr.cadastroclientes.repository;

import crude.tr.cadastroclientes.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT c FROM Client c WHERE (:name is null or :name = '' or lower(c.name) like lower(concat('%', :name,'%'))) ORDER BY c.name ASC")
    Page<Client>    findClientsByName(String name, Pageable pageable);

    Optional<Client> findByRegistrationNumber(String registrationNumber);
}
