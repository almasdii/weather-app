package testingSpring.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import testingSpring.dao.UserDao;
import testingSpring.dto.UserLoginRequest;
import testingSpring.entity.User;

import java.util.Optional;

@Component
public class UserLoginValidator implements Validator {
    private final UserDao userDao;

    @Autowired
    public UserLoginValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserLoginRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLoginRequest loginRequest = (UserLoginRequest) target;

        Optional<User> userOptional
                = userDao.findByLogin(loginRequest.login());

        if (userOptional.isEmpty()){
            errors.rejectValue("name","","User is not exist with this login");
        }
        if(userOptional.isPresent()){

        }

    }
}
