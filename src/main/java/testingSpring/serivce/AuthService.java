package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;
import testingSpring.dto.SessionLoginResponse;
import testingSpring.dto.UserLoginRequest;
import testingSpring.dto.UserRegisterRequest;
import testingSpring.entity.User;
import testingSpring.entity.WeatherSession;
import testingSpring.exception.BadUserCredentialsException;
import testingSpring.mapper.SessionMapper;
import testingSpring.mapper.SessionMapperImpl;
import testingSpring.util.SessionParams;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {
    private final UserDao userDao;
    private final SessionService sessionService;
    private final SessionMapper mapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @Autowired
    public AuthService(UserDao dao, SessionService sessionService, SessionMapperImpl mapper, UserDao userDao) {
        this.userDao = dao;
        this.sessionService = sessionService;
        this.mapper = mapper;
    }

    public SessionLoginResponse authenticate(UserLoginRequest dto){
        User user = userDao.findByLogin(dto.login())
                .orElseThrow(() -> new BadUserCredentialsException("Login or Password is incorrect"));
        log.debug("user hash password in db : {} and {} ",user.getPassword(),dto.password());
        if(!encoder.matches(dto.password(), user.getPassword())){
            throw new BadUserCredentialsException("Login or password is incorrect");
        }

        sessionService.removeOldSessionsByUserId(user.getId());
        WeatherSession session = sessionService.createSession(user.getId());
        log.debug("New Session created with UUID :  {} userID : {} , created at : {}" ,session.getId(),session.getUserId(),session.getCreatedAt());
        return mapper.sessionToSessionLoginResponse(session);
    }

    public boolean isAuthenticated(String sessionUuid) {
        UUID uuid = UUID.fromString(sessionUuid);
        Optional<WeatherSession> session = sessionService.findById(uuid);
        if(session.isEmpty()){
            log.debug("Session is Empty");
            return false;
        }
        log.debug("in AuthService Session : {}",session.get());

        if(isExpired(session.get().getCreatedAt())){
            return false;
        }

        Long userId = session.get().getUserId();
        Optional<User> userOptional = userDao.find(userId);
        return userOptional.isPresent();
    }

    private boolean isExpired(LocalDateTime createdAt){
        return LocalDateTime.now().isAfter(createdAt.plusMinutes(SessionParams.maxSessionTime));
    }

    public void register(UserRegisterRequest dto){
        //validation
        //mapping
        //dao.save

        User user = new User(dto.login(),encoder.encode(dto.password()));
        userDao.save(user);
    }

    public void logout(String sessionUuid) {
        boolean delete = userDao.delete(UUID.fromString(sessionUuid));
    }
}
