package crude.tr.cadastroclientes.dto;

public class AccountantDTO {
    private Long id;
    private String registrationNumber;
    private String accountantCode;
    private String name;
    private Boolean isActive;

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
}
