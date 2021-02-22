package salary.payment.exceptions;

public class InsufficientInventoryException extends Exception {
<<<<<<< HEAD
    public InsufficientInventoryException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
=======
    public InsufficientInventoryException(String message) {
        super(message);
>>>>>>> origin/main
    }

    public InsufficientInventoryException() {
    }
<<<<<<< HEAD

    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
=======
>>>>>>> origin/main
}
