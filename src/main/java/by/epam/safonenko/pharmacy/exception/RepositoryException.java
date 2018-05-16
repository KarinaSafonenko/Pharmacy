package by.epam.safonenko.pharmacy.exception;

public class RepositoryException extends Exception {
    public RepositoryException() {}
    public RepositoryException(String message, Throwable exception) {
        super(message, exception);
    }
    public RepositoryException(String message) {
        super(message);
    }
    public RepositoryException (Throwable exception) {
        super(exception);
    }
}
