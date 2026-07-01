package testingSpring.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import testingSpring.serivce.LocationService;
import testingSpring.serivce.UserService;

@Slf4j
@Controller
public class IndexController {
    private final UserService userService;
    private final LocationService locationService;
    @Autowired
    public IndexController(UserService userService, LocationService locationService) {
        this.userService = userService;
        this.locationService = locationService;
    }

    @GetMapping
    public String indexPage(Model model, HttpServletRequest request){
        return "index";
    }
}
