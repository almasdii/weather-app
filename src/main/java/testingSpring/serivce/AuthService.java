package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import testingSpring.dao.SessionDao;
import testingSpring.dao.UserDao;
import testingSpring.dto.UserLoginRequest;
import testingSpring.dto.UserRegisterRequest;
import testingSpring.entity.User;
import testingSpring.entity.WeatherSession;
import testingSpring.exception.UserNotFoundException;
import testingSpring.util.SessionParameters;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AuthService {
    private final UserDao userDao;
    private final SessionService sessionService;
    private final SessionDao sessionDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(SessionService sessionService, UserDao userDao, SessionDao sessionDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.sessionService = sessionService;
        this.sessionDao = sessionDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UUID signIn(UserLoginRequest dto){
        User user = userDao.findByLogin(dto.login())
                .orElseThrow(() -> new UserNotFoundException("No user found with this login : " + dto.login()));
        WeatherSession session = sessionService.create(user);
        return session.getId();
    }
    public boolean isPasswordMatch(String currentPassword,String targetPassword){
        return passwordEncoder.matches(currentPassword, targetPassword);
    }

    public boolean isAuthenticated(UUID sessionUuid) {
        Optional<WeatherSession> session = sessionDao.findById(sessionUuid);
        if(session.isEmpty()){
            log.debug("Session is Empty");
            return false;
        }
        if(isExpired(session.get().getExpires_at())){
            return false;
        }
        return session.get().getUser() != null;
    }

    private boolean isExpired(LocalDateTime createdAt){
        return LocalDateTime.now().isAfter(createdAt.plusMinutes(SessionParameters.MAX_SESSION_MINUTES));
    }

    @Transactional
    public void register(UserRegisterRequest dto){
        User user = new User(dto.login(),passwordEncoder.encode(dto.password()));
        userDao.save(user);
    }

    @Transactional
    public void logout(UUID sessionUuid) {
        sessionService.remove(sessionUuid);
    }
}
