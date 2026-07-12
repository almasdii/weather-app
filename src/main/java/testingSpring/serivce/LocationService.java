package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.api.OpenWeatherClient;
import testingSpring.dao.LocationDao;
import testingSpring.dto.LocationDetailsView;
import testingSpring.dto.LocationResponse;
import testingSpring.dto.LocationSearchView;
import testingSpring.entity.Location;
import testingSpring.entity.User;
import testingSpring.mapper.LocationMapper;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
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

    public List<LocationDetailsView> findAll(String userSession) throws IOException, InterruptedException {
        User user = userService.findBySessionId(userSession);
        List<Location> byUserId = locationDao.findByUserId(user.getId());
        byUserId.forEach((location)-> log.debug("users locations : {}",location.getName()));
        List<LocationResponse> locationResponses
                = locationMapper.locationListToLocationResponseList(byUserId);
        return openWeatherClient.findAll(locationResponses);
    }

    public List<LocationSearchView> search(String name) {
        try {
            return openWeatherClient.search(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
