package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testingSpring.api.OpenWeatherClient;
import testingSpring.dao.LocationDao;
import testingSpring.dto.LocationAddRequest;
import testingSpring.dto.LocationDetailsView;
import testingSpring.dto.LocationResponse;
import testingSpring.dto.LocationSearchView;
import testingSpring.entity.Location;
import testingSpring.entity.User;
import testingSpring.mapper.LocationMapper;

import java.io.IOException;
import java.math.BigDecimal;
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
        List<Location> byUserId = locationDao.findByUserId(user.getId());
        byUserId.forEach((location)-> log.debug("users locations : {}",location.getName()));
        List<LocationResponse> locationResponses
                = locationMapper.locationListToLocationResponseList(byUserId);
        return openWeatherClient.findAll(locationResponses);
    }

    public List<LocationSearchView> search(String name) {
        try {
            return openWeatherClient.search(name);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLocation(LocationAddRequest locationAddRequest, UUID sessionUUID) {
        User user = userService.findBySessionId(sessionUUID);
        Location location = new Location(locationAddRequest.name()
                ,user
                ,BigDecimal.valueOf(locationAddRequest.lat())
                ,BigDecimal.valueOf(locationAddRequest.lon()));
        locationDao.save(location);
    }

    public void delete(String name) {
        locationDao.remove(name);
    }
}
