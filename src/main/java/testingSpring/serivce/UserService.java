package testingSpring.serivce;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;
import testingSpring.dto.SessionDto;
import testingSpring.dto.UserLoginDto;
import testingSpring.dto.UserRegisterDto;
import testingSpring.entity.User;
import testingSpring.entity.WeatherSession;
import testingSpring.exception.BadUserCredentialsException;
import testingSpring.mapper.SessionDtoMapper;

@Service
public class UserService {
    private final UserDao dao;
    private final SessionService service;
    private final SessionService sessionService;
    private final SessionDtoMapper mapper;


    @Autowired
    public UserService(UserDao dao, SessionService service, SessionService sessionService, SessionDtoMapper mapper) {
        this.dao = dao;
        this.service = service;
        this.sessionService = sessionService;
        this.mapper = mapper;
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
    public SessionDto signIn(UserLoginDto dto){
        User user = dao.findByLogin(dto.login())
                .orElseThrow(() -> new BadUserCredentialsException("Login or Password is incorrect"));

        sessionService.removeOldSessionsByUserId(user.getId());
        WeatherSession session = sessionService.createSession(user.getId());
        return mapper.toSessionDto(session);
    }
}
