package crude.tr.cadastroclientes.model;

public enum CompanyStatus {
    ACTIVE("Ativo"),
    INACTIVE("Inativo"),
    SUSPENDED("Suspenso");

    private final String status;

    CompanyStatus(String status){
        this.status = status;
    }

    public static CompanyStatus fromString(String text) {
        for (CompanyStatus status : CompanyStatus.values()) {
            if (status.status.equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid company status: " + text);
    }
}
