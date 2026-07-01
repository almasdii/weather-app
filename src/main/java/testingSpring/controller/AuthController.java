package testingSpring.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import testingSpring.dto.SessionLoginResponse;
import testingSpring.dto.UserLoginRequest;
import testingSpring.dto.UserRegisterRequest;
import testingSpring.serivce.AuthService;
import testingSpring.util.SessionParams;
import testingSpring.util.UserValidator;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserValidator validator;
    private final AuthService service;

    @Autowired
    public AuthController(UserValidator validator, AuthService service) {
        this.validator = validator;
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
        if (result.hasErrors()){
            return "sign-in-with-errors";
        }
        SessionLoginResponse sessionLoginResponse = service.authenticate(userLoginRequest);
        setCookie(response, sessionLoginResponse.id().toString());
        return "redirect:/";
    }

    private void setCookie(HttpServletResponse response, String sessionValue){
        Cookie cookie = new Cookie(SessionParams.SESSION_UUID, sessionValue);
        cookie.setMaxAge(1200);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/weather");
        response.addCookie(cookie);
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(@ModelAttribute("userRegisterRequest") UserRegisterRequest userRegisterRequest) {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute("userRegisterRequest") @Valid UserRegisterRequest userRegisterRequest, BindingResult result) {
        log.debug("User register request : {} ",userRegisterRequest);
        validator.validate(userRegisterRequest,result);

        if(result.hasErrors()){
            return "sign-up-with-errors";
        }

        service.register(userRegisterRequest);
        return "redirect:/auth/sign-in";
    }

    @PostMapping(value = "/sign-out")
    public String signOut(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        Optional<String> sessionValue = getSessionValue(cookies);
        if (sessionValue.isEmpty()){
            return "redirect:/auth/sign-in";
        }
        expiresSession(response,sessionValue.get());
        service.logout(sessionValue.get());
        return "redirect:/auth/sign-in";
    }
    private void expiresSession(HttpServletResponse response,String session){
        Cookie cookie = new Cookie(SessionParams.SESSION_UUID, session);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/weather");
        response.addCookie(cookie);
    }
    private Optional<String> getSessionValue(Cookie[] cookies){
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SessionParams.SESSION_UUID))
                .map(Cookie::getValue)
                .findFirst();
    }
}
