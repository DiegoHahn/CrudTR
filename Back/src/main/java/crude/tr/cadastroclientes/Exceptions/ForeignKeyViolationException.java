package crude.tr.cadastroclientes.Exceptions;

public class ForeignKeyViolationException extends Exception {
    public ForeignKeyViolationException(String message) {
        super(message);
    }
}
