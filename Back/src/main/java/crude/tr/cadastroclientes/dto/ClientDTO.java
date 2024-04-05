package crude.tr.cadastroclientes.dto;

import crude.tr.cadastroclientes.model.CompanyStatus;
import crude.tr.cadastroclientes.model.RegistrationType;

public class ClientDTO {
    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }

    private RegistrationType registrationType;
    private String registrationNumber;
    private String clientCode;
    private String name;
    private String fantasyName;
    private String registrationDate; // Recebe String para simplificar a entrada, mas precisa converter depois
    private CompanyStatus companyStatus;
    private Long accountantId; // ID do contador em vez do objeto inteiro

    public String getRegistrationNumber() {
        return registrationNumber;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public CompanyStatus getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(CompanyStatus companyStatus) {
        this.companyStatus = companyStatus;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }


    public Long getAccountantId() {
        return accountantId;
    }

    public void setAccountantId(Long accountantId) {
        this.accountantId = accountantId;
    }
}