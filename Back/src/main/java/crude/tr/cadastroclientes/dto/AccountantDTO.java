package crude.tr.cadastroclientes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

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

    public AccountantDTO(Long id, String registrationNumber, String accountantCode, String name, Boolean isActive) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.accountantCode = accountantCode;
        this.name = name;
        this.isActive = isActive;
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

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }


    // Método equals e hashCode para comparar se objetos são iguais
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //  Verifica se as referências dos objetos são as mesmas
        if (o == null || getClass() != o.getClass()) return false; // Verifica se o objeto é do mesmo tipo
        AccountantDTO that = (AccountantDTO) o;
        return Objects.equals(id, that.id) && // Compara cada campo relevante
                Objects.equals(registrationNumber, that.registrationNumber) &&
                Objects.equals(accountantCode, that.accountantCode) &&
                Objects.equals(name, that.name) &&
                Objects.equals(isActive, that.isActive);
    }

    // Método hashCode para gerar um código hash para o objeto baseado nos campos relevantes
    @Override
    public int hashCode() {
        return Objects.hash(id, registrationNumber, accountantCode, name, isActive);
    }
}