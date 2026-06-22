package testingSpring.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.LocationDao;
import testingSpring.dto.LocationDto;
import testingSpring.entity.Location;

import java.util.List;

@Service
public class LocationService {
    private final LocationDao dao;

    @Autowired
    public LocationService(LocationDao dao) {
        this.dao = dao;
    }
    public List<LocationDto> findByUserId(Long id){
        List<Location> byUserId = dao.findByUserId(id);


        return List.of(new LocationDto[]{});
    }
}
