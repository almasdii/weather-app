package weather.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import weather.dto.UserLoginRequest;
import weather.entity.User;
import weather.service.AuthService;
import weather.service.UserService;

import java.util.Optional;

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
        return UserLoginRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLoginRequest loginRequest = (UserLoginRequest) target;

        Optional<User> userOptional
                = userService.findByLogin(loginRequest.login());

        if (userOptional.isEmpty()){
            errors.rejectValue("login","","User is not exist with this login");
            return;
        }
        User user = userOptional.get();
        if(!authService.isPasswordMatch(loginRequest.password(), userOptional.get().getPassword())){
            errors.rejectValue("password","","Name or password is incorrect");
        }


    }
}
