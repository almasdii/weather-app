package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;
import testingSpring.entity.User;
import testingSpring.exception.UserNotFoundException;

@Slf4j
@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findBySessionId(String userSession) {
        User user = userDao.findBySessionId(userSession).orElseThrow(() -> new UserNotFoundException("User not found with this session"));
        return user;
    }
}
