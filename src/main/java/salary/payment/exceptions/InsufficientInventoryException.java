package salary.payment.exceptions;

public class InsufficientInventoryException extends Exception {
    public InsufficientInventoryException(String message) {
        super(message);
    }

    public InsufficientInventoryException() {
    }
}
