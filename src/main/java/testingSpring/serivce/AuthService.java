package testingSpring.serivce;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testingSpring.dto.SessionDto;
import testingSpring.dto.UserLoginDto;
import testingSpring.entity.User;
import testingSpring.exception.BadUserCredentialsException;

@Service
public class AuthService {
    private final SessionService sessionService;
    private final UserService userService;

    @Autowired
    public AuthService(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

}
