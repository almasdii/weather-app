package testingSpring.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.SessionDao;
import testingSpring.entity.WeatherSession;

import java.util.UUID;

@Service
public class SessionService {
    private final SessionDao sessionDao;

    @Autowired
    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }


    public void removeOldSessionsByUserId(Long id) {
        sessionDao.removeByUserId(id);
    }


    public WeatherSession createSession(Long id) {
        return new WeatherSession(UUID.randomUUID(),3L);
    }
}
