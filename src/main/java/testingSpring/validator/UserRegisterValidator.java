package testingSpring.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import testingSpring.dto.UserCredentialsRequest;
import testingSpring.entity.User;
import testingSpring.serivce.UserService;

import java.util.Optional;

@Slf4j
@Component
public class UserRegisterValidator implements Validator {


    private final UserService userService;

    public UserRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCredentialsRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCredentialsRequest user = (UserCredentialsRequest) target;
        log.debug("login and password : {} , {} ",user.login(),user.password());
        Optional<User> userOptional = userService.findByLogin(user.login());
        if(userOptional.isPresent()){
            errors.rejectValue("login","","user login is already taken");
        }
        if(!user.password().equals(user.rePassword())){
            errors.rejectValue("password","","password and repeat password must be the same");
        }
    }
}
