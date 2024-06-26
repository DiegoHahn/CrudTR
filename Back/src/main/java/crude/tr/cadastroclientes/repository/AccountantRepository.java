package crude.tr.cadastroclientes.repository;

import crude.tr.cadastroclientes.model.Accountant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountantRepository extends JpaRepository<Accountant, Long> {
    @Query("SELECT a FROM Accountant a WHERE (:name is null or :name = '' or lower(a.name) like lower(concat('%', :name,'%'))) ORDER BY a.name ASC")
    Page<Accountant> findAccountantsByName(String name, Pageable pageable);

    Optional<Accountant> findByRegistrationNumber(String registrationNumber);
}