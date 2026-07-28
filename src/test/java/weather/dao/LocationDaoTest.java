package weather.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import weather.config.TestConfig;
import weather.entity.Location;
import weather.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class LocationDaoTest {

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private UserDao userDao;

    private Location location1;
    private Location location2;
    private User user;

    @BeforeEach
    void setLocation(){
        user = new User("Almas","Almas0224");
        location1 = new Location("Almaty",user,BigDecimal.valueOf(73),BigDecimal.valueOf(40),"KZ");
        location2 = new Location("Astana",user,BigDecimal.valueOf(10),BigDecimal.valueOf(20),"KZ");
    }

    @Test
    void findByIdShouldReturnLocationAfterSave(){
        userDao.save(user);

        locationDao.save(location1);

        Optional<Location> optionalLocation = locationDao.findById(location1.getId());

        assertThat(optionalLocation).isPresent();
        Location location = optionalLocation.get();

        assertThat(location.getId()).isGreaterThan(0);
    }

    @Test
    void findByUserIdShouldReturnTwoLocationsAfterSave(){
        userDao.save(user);

        locationDao.save(location1);
        locationDao.save(location2);

        List<Location> locations = locationDao.findByUserId(user.getId());

        assertThat(locations).hasSize(2);
    }

    @Test
    void findByIdShouldReturnEmptyAfterRemove(){
        userDao.save(user);

        Location save = locationDao.save(location1);

        locationDao.remove(location1.getId());

        Optional<Location> optionalLocation = locationDao.findById(location1.getId());

        assertThat(optionalLocation).isEmpty();
    }

    @Test
    void findByIdShouldReturnEmptyAfterRemoveByLatAndLon(){
        userDao.save(user);

        locationDao.save(location1);

        locationDao.removeByLatAndLon(location1.getLatitube().doubleValue(),location1.getLongitube().doubleValue());

        Optional<Location> optionalLocation = locationDao.findById(location1.getId());

        assertThat(optionalLocation).isEmpty();

    }


}
