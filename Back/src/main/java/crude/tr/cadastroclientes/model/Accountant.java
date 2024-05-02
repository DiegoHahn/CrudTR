package crude.tr.cadastroclientes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "accountants")
public class Accountant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationNumber;
    private String accountantCode;
    private String name;
    private Boolean isActive;

    //Não carrerga a lista de clientes quando buscar um contador
    @JsonIgnore
    @OneToMany(mappedBy = "accountant", cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private List<Client> clients;

    public Accountant() {
    }

    public Accountant(Long id, String registrationNumber, String accountantCode, String name, Boolean isActive) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.accountantCode = accountantCode;
        this.name = name;
        this.isActive = isActive;

    }
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAccountantCode() {
        return accountantCode;
    }

    public void setAccountantCode(String accountantCode) {
        this.accountantCode = accountantCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        clients.forEach(c -> c.setAccountant(this));
        this.clients = clients;
    }

}
