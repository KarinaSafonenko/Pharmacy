package by.epam.safonenko.pharmacy.exception;

public class CustomTagException extends RuntimeException {
    public CustomTagException() {
    }

    public CustomTagException(String message) {
        super(message);
    }

    public CustomTagException(String message, Throwable exception) {
        super(message, exception);
    }

    public CustomTagException(Throwable exception) {
        super(exception);
    }
}
