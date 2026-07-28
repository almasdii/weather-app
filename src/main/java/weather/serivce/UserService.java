package weather.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weather.dao.UserDao;
import weather.entity.User;
import weather.exception.UserNotFoundException;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findBySessionId(UUID uuid) {
        return userDao.findBySessionId(uuid).orElseThrow(() -> new UserNotFoundException("User not found with this session uuid " + uuid));
    }

    public Optional<User> findByLogin(String name) {
        return userDao.findByLogin(name);
    }
}
