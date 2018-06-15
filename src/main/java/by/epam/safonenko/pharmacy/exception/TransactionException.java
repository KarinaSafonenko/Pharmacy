package by.epam.safonenko.pharmacy.exception;

public class TransactionException extends Exception {
    public TransactionException() {}
    public TransactionException(String message, Throwable exception) {
        super(message, exception);
    }
    public TransactionException(String message) {
        super(message);
    }
    public TransactionException (Throwable exception) {
        super(exception);
    }
}
