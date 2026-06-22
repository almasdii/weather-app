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
import testingSpring.dto.SessionDto;
import testingSpring.dto.UserLoginDto;
import testingSpring.dto.UserRegisterDto;
import testingSpring.serivce.UserService;

@Slf4j
@Controller
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService service;

    @Autowired
    public AuthController(UserService service) {
        this.service = service;
    }

    @GetMapping(value = "/sign-in")
    public String signInPage(@ModelAttribute("userLoginDto") UserLoginDto userLoginDto) {
        return "sign-in";
    }

    @PostMapping(value = "/sign-in")
    public String signIn(@ModelAttribute("userLoginDto") @Valid UserLoginDto userLoginDto,
                         BindingResult result,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        log.info("user credentials {} , {} ",userLoginDto.login(),userLoginDto.password());
        if (result.hasErrors()){
            return "sign-in-with-errors";
        }
        SessionDto sessionDto = service.signIn(userLoginDto);
        Cookie cookie = new Cookie("SessionUUID",sessionDto.id().toString());
        cookie.setMaxAge(1200);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/home");
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping(value = "/sign-up")
    public String signUpPage(@ModelAttribute("userRegisterDto")  UserRegisterDto userRegisterDto) {
        return "sign-up";
    }

    @PostMapping(value = "/sign-up")
    public String signUp(@ModelAttribute("userRegisterDto") @Valid UserRegisterDto userRegisterDto , BindingResult result) {
        if(result.hasErrors()){
            return "sign-up-with-errors";
        }
        System.out.println(userRegisterDto);
        service.registerUser(userRegisterDto);
        return "redirect:/auth/sign-in";
    }
}
