package weather.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import weather.dto.UserRegisterRequest;
import weather.entity.User;
import weather.serivce.UserService;

import java.util.Optional;

@Component
public class UserRegisterValidator implements Validator {


    private final UserService userService;

    public UserRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterRequest user = (UserRegisterRequest) target;
        Optional<User> userOptional = userService.findByLogin(user.login());
        if(userOptional.isPresent()){
            errors.rejectValue("login","","user login is already taken");
        }
        if(!user.password().equals(user.rePassword())){
            errors.rejectValue("password","","password and repeat password must be the same");
        }
    }
}
