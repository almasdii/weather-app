package testingSpring.serivce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;

@Slf4j
@Service
public class UserService {
    private final UserDao dao;
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao dao, UserDao userDao) {
        this.dao = dao;
        this.userDao = userDao;
    }




}
