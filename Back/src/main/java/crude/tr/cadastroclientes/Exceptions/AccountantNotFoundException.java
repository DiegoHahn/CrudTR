package crude.tr.cadastroclientes.Exceptions;

public class AccountantNotFoundException extends RuntimeException {
    public AccountantNotFoundException(String message) {
        super(message);
    }
}