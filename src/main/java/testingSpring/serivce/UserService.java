package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;
import testingSpring.entity.User;
import testingSpring.exception.UserNotFoundException;

import java.util.UUID;

@Slf4j
@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findBySessionId(String userSession) {
        UUID uuid = UUID.fromString(userSession);
        return userDao.findBySessionId(uuid).orElseThrow(() -> new UserNotFoundException("User not found with this session"));
    }
}
