package testingSpring.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.api.OpenWeatherApi;
import testingSpring.dao.LocationDao;
import testingSpring.dto.LocationDetailsView;
import testingSpring.dto.LocationResponse;
import testingSpring.entity.Location;
import testingSpring.entity.User;
import testingSpring.mapper.LocationMapper;

import java.io.IOException;
import java.util.List;

@Service
public class LocationService {
    private final LocationDao locationDao;
    private final UserService userService;
    private final OpenWeatherApi openWeatherApi;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationDao locationDao, UserService userService, OpenWeatherApi openWeatherApi, LocationMapper locationMapper) {
        this.locationDao = locationDao;
        this.userService = userService;
        this.openWeatherApi = openWeatherApi;
        this.locationMapper = locationMapper;
    }

    public List<LocationDetailsView> findAll(String userSession) throws IOException, InterruptedException {
        User user = userService.findBySessionId(userSession);
        List<Location> byUserId = locationDao.findByUserId(user.getId());
        List<LocationResponse> locationResponses
                = locationMapper.locationListToLocationResponseList(byUserId);

        openWeatherApi.findAll(locationResponses);
    }
}
