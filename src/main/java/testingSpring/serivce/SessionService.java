package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testingSpring.dao.SessionDao;
import testingSpring.entity.WeatherSession;
import testingSpring.exception.SessionNotFoundException;

import java.util.Optional;
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
    public WeatherSession create(Long userId) {
        UUID uuid = UUID.randomUUID();
        WeatherSession session = new WeatherSession(uuid,userId);
        return sessionDao.save(session);
    }

    public Optional<WeatherSession> findById(UUID uuid) {
        Optional<WeatherSession> weatherSession = sessionDao.find(uuid).orElseThrow(() -> new SessionNotFoundException());
    }
}

