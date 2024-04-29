package crude.tr.cadastroclientes.model;

public enum RegistrationType {
    CPF("CPF"),
    CNPJ("CNPJ");

    private final String registrationType;

    RegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public static RegistrationType fromString(String text) {
        for (RegistrationType registrationType : RegistrationType.values()) {
            if (registrationType.registrationType.equalsIgnoreCase(text)) {
                return registrationType;
            }
        }
        throw new IllegalArgumentException("Nenhum tipo de cadastro encontrado para a string fornecida: " + text);
    }
}
