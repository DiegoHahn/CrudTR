package crude.tr.cadastroclientes.Exceptions;

public class InvalidCPFException extends RuntimeException {
    public InvalidCPFException(String message) {
        super(message);
    }
}