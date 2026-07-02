package testingSpring.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import testingSpring.serivce.LocationService;

import java.io.IOException;

@Controller
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public String indexPage(Model model, HttpServletRequest request) throws IOException, InterruptedException {
        String userSession = (String) request.getAttribute("userSession");
        locationService.findAll(userSession);
        return "index";
    }
}
