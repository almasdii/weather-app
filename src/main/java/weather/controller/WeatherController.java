package weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import weather.service.LocationService;

@Controller
public class WeatherController {
    private final LocationService service;

    @Autowired
    public WeatherController(LocationService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model){

        return "index";
    }
}
