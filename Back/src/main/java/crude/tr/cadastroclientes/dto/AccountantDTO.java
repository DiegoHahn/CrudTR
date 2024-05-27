package crude.tr.cadastroclientes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class AccountantDTO {

    private Long id;

    @NotNull(message = "O número de registro é obrigatório.")
    @Pattern(regexp = "[0-9]+", message = "O número de registro deve conter apenas dígitos.")
    @ValidCPF(message = "CPF inválido.")
    private String registrationNumber;

    @NotNull(message = "O código de registro é obrigatório.")
    @Pattern(regexp = "[0-9]+", message = "O código de registro deve conter apenas numeros.")
    private String accountantCode;

    @NotNull(message = "O campo nome é obrigatório.")
    @Length(max = 250, message = "O nome deve conter no máximo 250 caracteres.")
    private String name;

    @NotNull(message = "O campo de status é obrigatório.")
    private Boolean isActive;

    public AccountantDTO() {
    }

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

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }
}