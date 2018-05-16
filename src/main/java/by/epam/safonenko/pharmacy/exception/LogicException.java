package by.epam.safonenko.pharmacy.exception;

public class LogicException extends Exception {
    public LogicException() {}
    public LogicException(String message, Throwable exception) {
        super(message, exception);
    }
    public LogicException(String message) {
        super(message);
    }
    public LogicException (Throwable exception) {
        super(exception);
    }
}
