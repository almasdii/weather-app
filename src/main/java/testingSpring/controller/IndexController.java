package testingSpring.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import testingSpring.dto.LocationDto;
import testingSpring.serivce.LocationService;
import testingSpring.serivce.UserService;
import testingSpring.util.SessionParams;

import java.util.List;

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

    @GetMapping(value = "")
    public String homePage(Model model, HttpServletRequest request){
        return "index";
    }

}
