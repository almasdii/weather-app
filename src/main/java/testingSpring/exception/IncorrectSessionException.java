package testingSpring.exception;

public class IncorrectSessionException extends RuntimeException {
    public IncorrectSessionException(String message) {
        super(message);
    }

    public IncorrectSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
