package testingSpring.serivce;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;
import testingSpring.dto.SessionDto;
import testingSpring.dto.UserLoginDto;
import testingSpring.dto.UserRegisterDto;
import testingSpring.entity.User;

import java.util.Optional;

@Service
public class UserService {
    private final UserDao dao;
    private final SessionService service;

    @Autowired
    public UserService(UserDao dao, SessionService service) {
        this.dao = dao;
        this.service = service;
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


    public SessionDto createSession(UserLoginDto dto) {
        Optional<User> byLogin = dao.findByLogin(dto.login());


    }
}
