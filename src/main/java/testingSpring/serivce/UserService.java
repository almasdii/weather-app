package testingSpring.serivce;

import org.springframework.stereotype.Service;
import testingSpring.dao.UserDao;

@Service
public class UserService {
    private final UserDao dao;

}
