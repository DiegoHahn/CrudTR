package crude.tr.cadastroclientes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import crude.tr.cadastroclientes.model.CompanyStatus;
import crude.tr.cadastroclientes.model.RegistrationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.OffsetDateTime;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class ClientDTO {

    @JsonProperty("id")
    private Long id;
    private RegistrationType registrationType;

    @Pattern(regexp = "[0-9]+", message = "O número de registro deve conter apenas dígitos.")
    @ValidRegistration(message = "Número de regitro inválido.")
    private String registrationNumber;

    @NotNull(message = "O código de registro é obrigatório.")
    @Pattern(regexp = "[0-9]+", message = "O código de registro deve conter apenas numeros.")
    private String clientCode;

    @NotNull(message = "O campo nome é obrigatório.")
    @Length(max = 255, message = "O nome deve conter no máximo 250 caracteres.")
    private String name;
    private String fantasyName;

    @NotNull(message = "O campo de data é obrigatório.")
    private OffsetDateTime registrationDate;

    @NotNull(message = "O campo de data é obrigatório.")
    private CompanyStatus companyStatus;

    @NotNull(message = "O id do contador é obrigatório.")
    private Long accountantId;

    public ClientDTO(Long id, RegistrationType registrationType, String registrationNumber, String clientCode, String name, String fantasyName, OffsetDateTime registrationDate, CompanyStatus companyStatus, Long accountantId) {
        this.id = id;
        this.registrationType = registrationType;
        this.registrationNumber = registrationNumber;
        this.clientCode = clientCode;
        this.name = name;
        this.fantasyName = fantasyName;
        this.registrationDate = registrationDate;
        this.companyStatus = companyStatus;
        this.accountantId = accountantId;
    }
    public ClientDTO() {
    }

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

    public OffsetDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(OffsetDateTime registrationDate) {
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

    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(id, clientDTO.id) &&
                registrationType == clientDTO.registrationType &&
                Objects.equals(registrationNumber, clientDTO.registrationNumber) &&
                Objects.equals(clientCode, clientDTO.clientCode) &&
                Objects.equals(name, clientDTO.name) &&
                Objects.equals(fantasyName, clientDTO.fantasyName) &&
                Objects.equals(registrationDate, clientDTO.registrationDate) &&
                companyStatus == clientDTO.companyStatus &&
                Objects.equals(accountantId, clientDTO.accountantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registrationType, registrationNumber, clientCode, name, fantasyName, registrationDate, companyStatus, accountantId);
    }
}