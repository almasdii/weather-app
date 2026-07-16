package testingSpring.exception;

public class SessionNotFoundException extends RuntimeException {


    public SessionNotFoundException(Throwable cause) {
        super(cause);
    }
}
