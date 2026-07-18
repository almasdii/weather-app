package testingSpring.exception;

public class WeatherApiConnectionException extends RuntimeException {
    public WeatherApiConnectionException(String message) {
        super(message);
    }
}
