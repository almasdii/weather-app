package weather.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import weather.config.TestConfig;
import weather.entity.User;
import weather.entity.WeatherSession;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class SessionDaoTest {
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private UserDao userDao;

    private WeatherSession session;
    private User user;
    private UUID sessionUuid;

    @BeforeEach
    void setSession(){
        user = new User("Raxat","Raxat0224");
        sessionUuid = UUID.randomUUID();
        session = new WeatherSession(sessionUuid,user);
    }

    @Test
    @DisplayName("ale")
    void shouldReturnSessionWhenSave(){
        WeatherSession savedSession = sessionDao.save(session);

        assertThat(savedSession).isNotNull();
    }


    @Test
    void shouldReturnEmptyWhenFindByIdAfterRemove(){
        sessionDao.save(session);
        sessionDao.remove(session.getId());

        Optional<WeatherSession> optionalWeatherSession = sessionDao.findById(session.getId());

        assertThat(optionalWeatherSession).isEmpty();
    }

    @Test
    void shouldReturnEmptyAfterRemoveByUserId(){
        // arrange

        User savedUser = userDao.save(user);
        sessionDao.save(session);

        log.debug("saved user id : {}",savedUser.getId());
        int i = sessionDao.removeByUserId(savedUser.getId());
        log.debug("{} columns affected ",i);


        Optional<WeatherSession> optionalWeatherSession = sessionDao.findById(session.getId());

        assertThat(optionalWeatherSession).isEmpty();
    }
}
