package weather.exception;

public class WeatherApiParseException extends RuntimeException {
  public WeatherApiParseException(String message) {
    super(message);
  }

  public WeatherApiParseException(String message, Throwable cause) {
    super(message, cause);
  }
}
