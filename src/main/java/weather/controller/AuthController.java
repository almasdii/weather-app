package weather.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import weather.dto.UserLoginRequest;
import weather.dto.UserRegisterRequest;
import weather.serivce.AuthService;
import weather.util.SessionParameters;
import weather.validator.UserLoginValidator;
import weather.validator.UserRegisterValidator;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping(value = "/auth")
public class AuthController {
    private final UserRegisterValidator userRegisterValidator;
    private final UserLoginValidator userLoginValidator;
    private final AuthService service;

    @Autowired
    public AuthController(UserRegisterValidator userRegisterValidator, UserLoginValidator userLoginValidator, AuthService service) {
        this.userRegisterValidator = userRegisterValidator;
        this.userLoginValidator = userLoginValidator;
        this.service = service;
    }


    @GetMapping(value = "/sign-in")
    public String signInPage(@ModelAttribute("userLoginRequest") UserLoginRequest userLoginRequest) {
        return "sign-in";
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@ModelAttribute("userLoginRequest") @Valid UserLoginRequest userLoginRequest,
                         BindingResult result,
                         HttpServletResponse response) {

        log.info("user credentials {} , {} ", userLoginRequest.login(), userLoginRequest.password());
        userLoginValidator.validate(userLoginRequest,result);
        if (result.hasErrors()){
            return "sign-in-with-errors";
        }
        UUID sessionUuid = service.signIn(userLoginRequest);
        setCookie(response, sessionUuid.toString(), SessionParameters.MAX_SESSION_SECONDS);
        return "redirect:/locations";
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(@ModelAttribute("userRegisterRequest") UserRegisterRequest userRegisterRequest) {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute("userRegisterRequest") @Valid UserRegisterRequest userRegisterRequest, BindingResult result) {
        log.debug("User register request : {} ",userRegisterRequest.toString());
        userRegisterValidator.validate(userRegisterRequest,result);

        if(result.hasErrors()){
            return "sign-up-with-errors";
        }

        service.register(userRegisterRequest);
        return "redirect:/auth/sign-in";
    }

    @PostMapping(value = "/sign-out")
    public String signOut(@CookieValue("SessionUUID") UUID sessionUuid, HttpServletResponse response){
        if (sessionUuid == null){
            return "redirect:/auth/sign-in";
        }
        setCookie(response,sessionUuid.toString(),SessionParameters.EXPIRES_SESSION);
        service.logout(sessionUuid);
        return "redirect:/auth/sign-in";
    }
    private void setCookie(HttpServletResponse response, String sessionUuid,int maxAge){
        Cookie cookie = new Cookie(SessionParameters.SESSION_UUID, sessionUuid);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(SessionParameters.SET_HTTP_ONLY);
        cookie.setSecure(SessionParameters.SET_SECURE);
        cookie.setPath(SessionParameters.PATH);
        response.addCookie(cookie);
    }
}
