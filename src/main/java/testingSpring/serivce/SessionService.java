package testingSpring.serivce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.SessionDao;
import testingSpring.entity.WeatherSession;

import java.util.UUID;

@Service
public class SessionService {
    private final SessionDao sessionDao;
    private static final Long SESSION_LIFE_MINUTES = 20L;

    @Autowired
    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }


    public void removeOldSessionsByUserId(Long id) {
        sessionDao.removeByUserId(id);
    }


    public WeatherSession createSession(Long userId) {
        UUID uuid = UUID.randomUUID();
        WeatherSession session = new WeatherSession(uuid,userId);
        return sessionDao.save(session);
    }

    public void findById(String value) {

    }
}

