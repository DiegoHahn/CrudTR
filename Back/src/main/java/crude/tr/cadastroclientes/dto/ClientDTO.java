package crude.tr.cadastroclientes.dto;

import crude.tr.cadastroclientes.model.CompanyStatus;
import crude.tr.cadastroclientes.model.RegistrationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class ClientDTO {
    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }

    private Long id;
    private RegistrationType registrationType;

    @NotNull(message = "O número de registro é obrigatório.")
    private String registrationNumber;

    @NotNull(message = "O código de registro é obrigatório.")
    @Pattern(regexp = "[0-9]+", message = "O código de registro deve conter apenas numeros.")
    private String clientCode;

    @NotNull(message = "O campo nome é obrigatório.")
    @Length(max = 255, message = "O nome deve conter no máximo 250 caracteres.")
    private String name;
    private String fantasyName;

    @NotNull(message = "O campo de data é obrigatório.")
    private String registrationDate;


    @NotNull(message = "O campo de data é obrigatório.")
    private CompanyStatus companyStatus;

    @NotNull(message = "O id do contador é obrigatório.")
    private Long accountantId;

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