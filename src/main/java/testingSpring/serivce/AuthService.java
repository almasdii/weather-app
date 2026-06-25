package testingSpring.serivce;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;
import testingSpring.dto.SessionDto;
import testingSpring.dto.UserLoginDto;
import testingSpring.dto.UserRegisterDto;
import testingSpring.entity.User;
import testingSpring.entity.WeatherSession;
import testingSpring.exception.BadUserCredentialsException;
import testingSpring.exception.UserNotFoundException;
import testingSpring.mapper.SessionDtoMapper;
import testingSpring.mapper.SessionDtoMapperImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthService {
    private final UserDao dao;
    private final SessionService sessionService;
    private final SessionDtoMapper mapper;
    private final UserDao userDao;

    @Autowired
    public AuthService(UserDao dao, SessionService sessionService, SessionDtoMapperImpl mapper, UserDao userDao) {
        this.dao = dao;
        this.sessionService = sessionService;
        this.mapper = mapper;
        this.userDao = userDao;
    }

    public SessionDto signIn(UserLoginDto dto){
        User user = dao.findByLogin(dto.login())
                .orElseThrow(() -> new BadUserCredentialsException("Login or Password is incorrect"));

        sessionService.removeOldSessionsByUserId(user.getId());
        WeatherSession session = sessionService.createSession(user.getId());
        log.debug("New Session created with UUID :  {} userID : {} , created at : {}" ,session.getId(),session.getUserId(),session.getCreatedAt());
        return mapper.toSessionDto(session);
    }

    public boolean isAuthenticated(String value) {
        UUID uuid = UUID.fromString(value);
        Optional<WeatherSession> session = sessionService.findById(uuid);
        LocalDateTime localDateTime = LocalDateTime.now();
        if(session.isEmpty()){
            log.debug("Session is Empty");
            return false;
        }
        log.debug("in AuthService Session : {}",session.get());
        LocalDateTime createdAt = session.get().getCreatedAt();
        LocalDateTime localDateTime1 = createdAt.plusMinutes(20);

        if(localDateTime.isAfter(localDateTime1)){
            return false;
        }

        log.debug("now : {}, created + 20 minutes : {}",localDateTime,localDateTime1);

        Long userId = session.get().getUserId();
        userDao.find(userId).orElseThrow(()-> new UserNotFoundException("Not found"));
        return true;
    }

    @Transactional
    public void registerUser(UserRegisterDto dto){
        //validation
        //mapping
        //dao.save
        System.out.println("sdugvcah ptvgrhdfivobuyrge");
        User user = new User(dto.login(),dto.password());
        dao.save(user);
    }

}
