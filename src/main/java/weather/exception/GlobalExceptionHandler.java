package weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handle(UserNotFoundException exception, Model model){
        model.addAttribute("exception",exception.getMessage());
        return "error";
    }

    @ExceptionHandler(WeatherApiConnectionException.class)
    public String handle(WeatherApiConnectionException e, Model model){
        model.addAttribute("");
        return "error";
    }

    @ExceptionHandler(WeatherApiParseException.class)
    public String handle(WeatherApiParseException e,Model model){
        model.addAttribute("");
        return "error";
    }
}
