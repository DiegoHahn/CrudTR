package crude.tr.cadastroclientes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CompanyStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    SUSPENDED("Suspenso");

    private final String status;

    CompanyStatus(String status){
        this.status = status;
    }
    @JsonValue
    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static CompanyStatus fromString(String text) {
        for (CompanyStatus status : CompanyStatus.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid company status: " + text);
    }
}