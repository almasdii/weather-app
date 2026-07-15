package testingSpring.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import testingSpring.dto.LocationAddRequest;
import testingSpring.dto.LocationDetailsView;
import testingSpring.dto.LocationSearchView;
import testingSpring.serivce.LocationService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
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
        log.debug("User session value : {}",userSession);
        List<LocationDetailsView> all = locationService.findAll(userSession);
        model.addAttribute("locations",all);
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(value = "name") String name,Model model){
        List<LocationSearchView> search = locationService.search(name);
        model.addAttribute("locations",search);
        model.addAttribute("name",name);
        return "search-results";
    }


    @PostMapping("/add")
    public String addLocation(@ModelAttribute LocationAddRequest locationAddRequest, @CookieValue("SessionUUID") UUID sessionUUID) {
        log.debug("lon : {} , lat : {} ",locationAddRequest.lon(),locationAddRequest.lat());
//        return ResponseEntity.ok("");
        locationService.addLocation(locationAddRequest,sessionUUID);
        return "redirect:/";
    }

    @PostMapping("/location/delete")
    public String deleteLocation(@ModelAttribute("name") String name){
        log.debug("delete location name : {} ", name);
        locationService.delete(name);
        return "redirect:/";
    }
}
