package testingSpring.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import testingSpring.dao.UserDao;
import testingSpring.dto.UserRegisterRequest;
import testingSpring.entity.User;

import java.util.Optional;

@Component
public class UserRegisterValidator implements Validator {
    private final UserDao userDao;

    @Autowired
    public UserRegisterValidator(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterRequest user = (UserRegisterRequest) target;
        Optional<User> userOptional = userDao.findByLogin(user.login());
        if(userOptional.isPresent()){
            errors.rejectValue("login","","user login is already taken");
        }
        if(!user.password().equals(user.rePassword())){
            errors.rejectValue("password","","password and repeat password must be the same");
        }
    }
}
