package weather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weather.api.OpenWeatherClient;
import weather.dao.LocationDao;
import weather.dto.LocationAddRequest;
import weather.dto.LocationApiResponse;
import weather.dto.LocationDetailsView;
import weather.dto.LocationSearchView;
import weather.entity.Location;
import weather.entity.User;
import weather.mapper.LocationMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class LocationService {
    private final LocationDao locationDao;
    private final UserService userService;
    private final OpenWeatherClient openWeatherClient;

    @Autowired
    public LocationService(LocationDao locationDao, UserService userService, OpenWeatherClient openWeatherClient) {
        this.locationDao = locationDao;
        this.userService = userService;
        this.openWeatherClient = openWeatherClient;
    }

    public List<LocationDetailsView> findAll(UUID sessionValue) {
        User user = userService.findBySessionId(sessionValue);

        List<Location> locations = locationDao.findByUserId(user.getId());

        List<LocationDetailsView> locationDetailsViewsList = new ArrayList<>();

        for (Location location : locations) {
            LocationApiResponse locationApiResponse = openWeatherClient.searchByLatAndLon(location.getLatitube().doubleValue(), location.getLongitube().doubleValue());
            LocationDetailsView locationDetailsView
                    = new LocationDetailsView(locationApiResponse.temp()
                    , locationApiResponse.feelsLike()
                    , locationApiResponse.humidity()
                    , locationApiResponse.description()
                    , location.getCountry()
                    , locationApiResponse.icon()
                    , location.getName(),
                    location.getLatitube().doubleValue(),
                    location.getLongitube().doubleValue());
            locationDetailsViewsList.add(locationDetailsView);
        }

        return locationDetailsViewsList;
    }

    public List<LocationSearchView> searchByName(String name) {
        return openWeatherClient.searchByName(name);
    }

    @Transactional
    public void addLocation(LocationAddRequest locationAddRequest, UUID sessionUUID) {
        User user = userService.findBySessionId(sessionUUID);
        Location location = new Location(locationAddRequest.name(),
                user
                , BigDecimal.valueOf(locationAddRequest.lat())
                , BigDecimal.valueOf(locationAddRequest.lon())
                , locationAddRequest.country()
        );
        locationDao.save(location);
    }

    @Transactional
    public void deleteByLatAndLon(Double lan, Double lon) {
        locationDao.removeByLatAndLon(lan, lon);
    }
}
