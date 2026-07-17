package testingSpring.validator;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import testingSpring.dto.UserCredentialsRequest;
import testingSpring.entity.User;
import testingSpring.serivce.AuthService;
import testingSpring.serivce.UserService;

import java.util.Optional;

@Slf4j
@Component
public class UserLoginValidator implements Validator {


    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserLoginValidator(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCredentialsRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCredentialsRequest loginRequest = (UserCredentialsRequest) target;

        Optional<User> userOptional
                = userService.findByLogin(loginRequest.login());

        if (userOptional.isEmpty()){
            errors.rejectValue("name","","User is not exist with this login");
            return;
        }
        User user = userOptional.get();
        log.debug("login request password : {} ",loginRequest.password());
        log.debug("login request RePassword : {} ",loginRequest.rePassword());
        log.debug("is password match : {} ",authService.isPasswordMatch(loginRequest.password(), userOptional.get().getPassword()));
        if(!authService.isPasswordMatch(loginRequest.password(), userOptional.get().getPassword())){
            errors.rejectValue("password","","Name or password is incorrect");
        }


    }
}
