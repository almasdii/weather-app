package testingSpring.exception;

public class SessionNotFoundException extends RuntimeException {


    public SessionNotFoundException(Throwable cause) {
        super(cause);
    }

    public SessionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionNotFoundException(String message) {
        super(message);
    }
}
