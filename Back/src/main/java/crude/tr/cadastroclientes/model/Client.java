package crude.tr.cadastroclientes.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean registrationType;
    private String registrationNumber;
    private String clientCode;
    private String name;
    private String fantasyName;
    private LocalDate registrationDate;
    @Enumerated(EnumType.STRING)
    private CompanyStatus companyStatus;

    @ManyToOne
    private Accountant accountant;

    public Client() {
    }

    public Client(Boolean registrationType, String registrationNumber, String clientCode, String name, String fantasyName, LocalDate registrationDate, CompanyStatus companyStatus, Accountant accountant) {
        this.registrationType = registrationType;
        this.registrationNumber = registrationNumber;
        this.clientCode = clientCode;
        this.name = name;
        this.fantasyName = fantasyName;
        this.registrationDate = registrationDate;
        this.companyStatus = companyStatus;
        this.accountant = accountant;
    }

    public Boolean getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(Boolean registrationType) {
        this.registrationType = registrationType;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public CompanyStatus getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(CompanyStatus companyStatus) {
        this.companyStatus = companyStatus;
    }

    public Accountant getAccountant() {
        return accountant;
    }

    public void setAccountant(Accountant accountant) {
        this.accountant = accountant;
    }
}
