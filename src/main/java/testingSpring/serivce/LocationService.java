package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testingSpring.api.OpenWeatherClient;
import testingSpring.dao.LocationDao;
import testingSpring.dto.LocationAddRequest;
import testingSpring.dto.LocationDetailsView;
import testingSpring.dto.LocationSearchView;
import testingSpring.entity.Location;
import testingSpring.entity.User;
import testingSpring.mapper.LocationMapper;

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
    private final LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationDao locationDao, UserService userService, OpenWeatherClient openWeatherClient, LocationMapper locationMapper) {
        this.locationDao = locationDao;
        this.userService = userService;
        this.openWeatherClient = openWeatherClient;
        this.locationMapper = locationMapper;
    }

    public List<LocationDetailsView> findAll(UUID sessionValue) {
        User user = userService.findBySessionId(sessionValue);

        List<Location> locations = locationDao.findByUserId(user.getId());

        List<LocationDetailsView> locationDetailsViewsList = new ArrayList<>();

        for (Location location : locations) {
            LocationDetailsView locationDetailsView = openWeatherClient.searchByLatAndLon(location.getLatitube(), location.getLongitube());
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
        Location location = new Location(locationAddRequest.name()
                , user
                , BigDecimal.valueOf(locationAddRequest.lat())
                , BigDecimal.valueOf(locationAddRequest.lon()));
        locationDao.save(location);
    }

    @Transactional
    public void delete(String name) {
        locationDao.removeByName(name);
    }
}
