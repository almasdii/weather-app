package weather.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import weather.dto.LocationAddRequest;
import weather.dto.LocationDeleteRequest;
import weather.dto.LocationDetailsView;
import weather.dto.LocationSearchView;
import weather.serivce.LocationService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/locations")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public String indexPage(Model model, @CookieValue("SessionUUID") UUID sessionUuid){
        log.debug("User session uuid : {}",sessionUuid);
        List<LocationDetailsView> locationApiResponses = locationService.findAll(sessionUuid);
        locationApiResponses.stream().map(LocationDetailsView::lat).forEach(System.out::println);
        model.addAttribute("locations", locationApiResponses);
        return "index";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(value = "name") @NotBlank String name, Model model){
        List<LocationSearchView> search = locationService.searchByName(name);
        model.addAttribute("locations",search);
        model.addAttribute("name",name);
        return "search-results";
    }


    @PostMapping()
    public String addLocation(@ModelAttribute LocationAddRequest locationAddRequest, @CookieValue("SessionUUID") UUID sessionUUID) {
        log.debug("lon : {} , lat : {} ",locationAddRequest.lon(),locationAddRequest.lat());
        log.debug("add location info : {}",locationAddRequest.name());
        locationService.addLocation(locationAddRequest,sessionUUID);
        return "redirect:/locations";
    }


    @DeleteMapping()
    public String deleteLocation(@ModelAttribute LocationDeleteRequest locationDeleteRequest){
        log.debug("delete mapping called for : {} , {} ",locationDeleteRequest.latitube(),locationDeleteRequest.longitube());
        locationService.deleteByLatAndLon(locationDeleteRequest.latitube(),locationDeleteRequest.longitube());
        if(locationDeleteRequest.latitube() == null || locationDeleteRequest.longitube() == null){
            throw new IllegalArgumentException("Latitube or Longitube is null");
        }
        return "redirect:/locations";
    }
}
