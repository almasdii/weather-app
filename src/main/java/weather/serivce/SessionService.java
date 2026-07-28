package weather.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weather.dao.SessionDao;
import weather.entity.User;
import weather.entity.WeatherSession;
import weather.exception.SessionNotFoundException;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SessionService {
    private final SessionDao sessionDao;

    @Autowired
    public SessionService(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Transactional
    public void removeByUserId(Long userId) {
        int removedSessions = sessionDao.removeByUserId(userId);
        log.debug("{} - sessions removed",removedSessions);
    }

    @Transactional
    public WeatherSession create(User user) {
        removeByUserId(user.getId());

        UUID uuid = UUID.randomUUID();
        WeatherSession session = new WeatherSession(uuid,user);
        log.debug("New Session created : {} ",session.getId());
        return sessionDao.save(session);
    }

    public WeatherSession findById(UUID uuid) {
        return sessionDao.findById(uuid).orElseThrow(
                        () -> new SessionNotFoundException("Session not found with this id : " + uuid));
    }

    public void remove(UUID sessionUuid) {
        sessionDao.remove(sessionUuid);
    }
}

