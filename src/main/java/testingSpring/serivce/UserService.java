package testingSpring.serivce;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;
import testingSpring.dto.UserRegisterDto;
import testingSpring.entity.User;

@Service
public class UserService {
    private final UserDao dao;

    @Autowired
    public UserService(UserDao dao) {
        this.dao = dao;
    }
    @Transactional
    public void registerUser(UserRegisterDto dto){
        //validation
        //mapping
        //dao.save
        System.out.println("sdugvcah ptvgrhdfivobuyrge");
        User user = new User(dto.name(),dto.password());
        dao.save(user);
    }
}
