package testingSpring.controller;


import jakarta.servlet.http.Cookie;
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
import testingSpring.dto.SessionResponseDto;
import testingSpring.dto.UserLoginRequest;
import testingSpring.dto.UserRegisterRequest;
import testingSpring.serivce.AuthService;
import testingSpring.util.SessionParams;

@Slf4j
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping(value = "/sign-in")
    public String signInPage(@ModelAttribute("userLoginDto") UserLoginRequest userLoginRequest) {
        return "sign-in";
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@ModelAttribute("userLoginDto") @Valid UserLoginRequest userLoginRequest,
                         BindingResult result,
                         HttpServletResponse response) {
        log.info("user credentials {} , {} ", userLoginRequest.login(), userLoginRequest.password());
        if (result.hasErrors()){
            return "sign-in-with-errors";
        }
        SessionResponseDto sessionResponseDto = service.authenticate(userLoginRequest);
        setCookie(response,sessionResponseDto);
        return "redirect:/";
    }

    private void setCookie(HttpServletResponse response, SessionResponseDto sessionResponseDto){
        Cookie cookie = new Cookie(SessionParams.SESSION_UUID,sessionResponseDto.id().toString());
        cookie.setMaxAge(1200);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/home");
        response.addCookie(cookie);
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(@ModelAttribute("userRegisterDto") UserRegisterRequest userRegisterRequest) {
        return "sign-up";
    }


    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute("userRegisterDto") @Valid UserRegisterRequest userRegisterRequest, BindingResult result) {
        log.debug("User register request : {} ",userRegisterRequest);

        if(result.hasErrors()){
            return "sign-up-with-errors";
        }

        service.register(userRegisterRequest);
        return "redirect:/auth/sign-in";
    }
}
