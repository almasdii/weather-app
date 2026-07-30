package weather.exception;

public class WeatherApiConnectionException extends RuntimeException {
    public WeatherApiConnectionException(String message) {
        super(message);
    }

    public WeatherApiConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
